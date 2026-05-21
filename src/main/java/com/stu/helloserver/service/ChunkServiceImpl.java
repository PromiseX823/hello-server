package com.stu.helloserver.service;

import com.stu.helloserver.model.entity.DocumentChunk;
import com.stu.helloserver.service.ChunkService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkServiceImpl implements ChunkService {

    @Override
    public List<DocumentChunk> splitDocument(Long documentId, String content, int chunkSize, int overlap) {
        List<DocumentChunk> chunks = new ArrayList<>();
        if (content == null || content.isBlank()) {
            return chunks;
        }

        int start = 0;
        int index = 0;
        int totalLen = content.length();

        while (start < totalLen) {
            int end = Math.min(start + chunkSize, totalLen);
            String chunkText = content.substring(start, end);

            DocumentChunk chunk = new DocumentChunk();
            chunk.setDocumentId(documentId);
            chunk.setChunkIndex(index);
            chunk.setChunkContent(chunkText);
            chunk.setCreateTime(LocalDateTime.now());
            chunks.add(chunk);

            // 重叠：下一个起点 = 当前起点 + 块大小 - 重叠
            start = start + chunkSize - overlap;
            index++;
        }
        return chunks;
    }
}
