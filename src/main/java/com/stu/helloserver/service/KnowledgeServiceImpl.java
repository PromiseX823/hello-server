package com.stu.helloserver.service;

import com.stu.helloserver.mapper.DocumentChunkMapper;
import com.stu.helloserver.mapper.KnowledgeDocumentMapper;
import com.stu.helloserver.model.entity.DocumentChunk;
import com.stu.helloserver.model.entity.KnowledgeDocument;
import com.stu.helloserver.model.vo.KnowledgeUploadVo;
import com.stu.helloserver.parser.PdfParser;
import com.stu.helloserver.parser.TxtParser;
import com.stu.helloserver.service.KnowledgeService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {

    private final KnowledgeDocumentMapper knowledgeDocumentMapper;
    private final DocumentChunkMapper documentChunkMapper;
    private final TxtParser txtParser;
    private final PdfParser pdfParser;
    private final ChunkService chunkService;

    public KnowledgeServiceImpl(KnowledgeDocumentMapper knowledgeDocumentMapper,
                                DocumentChunkMapper documentChunkMapper,
                                TxtParser txtParser,
                                PdfParser pdfParser,
                                ChunkService chunkService) {
        this.knowledgeDocumentMapper = knowledgeDocumentMapper;
        this.documentChunkMapper = documentChunkMapper;
        this.txtParser = txtParser;
        this.pdfParser = pdfParser;
        this.chunkService = chunkService;
    }

    @Override
    public KnowledgeUploadVo upload(MultipartFile file) {
        // 1. 文件校验
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("文件为空，无法上传");
        }
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new RuntimeException("文件超过10MB限制");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new RuntimeException("文件名不能为空");
        }

        // 2. 获取文件类型
        String fileType = getFileType(originalFilename).toLowerCase();
        if (!"txt".equals(fileType) && !"pdf".equals(fileType)) {
            throw new RuntimeException("仅支持txt/pdf格式");
        }

        // 3. 解析文本
        String content;
        try {
            if ("txt".equals(fileType)) {
                content = txtParser.parse(file);
            } else {
                content = pdfParser.parse(file);
            }
        } catch (Exception e) {
            throw new RuntimeException("文件解析失败：" + e.getMessage(), e);
        }

        // 4. 保存文档
        KnowledgeDocument doc = new KnowledgeDocument();
        doc.setFileName(originalFilename);
        doc.setFileType(fileType);
        doc.setContent(content);
        doc.setCreateTime(LocalDateTime.now());
        knowledgeDocumentMapper.insert(doc);

        // 5. 自动切块（300字/块，重叠50字）
        List<DocumentChunk> chunks = chunkService.splitDocument(doc.getId(), content, 300, 50);
        for (DocumentChunk chunk : chunks) {
            documentChunkMapper.insert(chunk);
        }

        // 6. 返回结果
        return new KnowledgeUploadVo(
                originalFilename,
                fileType,
                content.length(),
                doc.getId()
        );
    }

    private String getFileType(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1);
    }
}
