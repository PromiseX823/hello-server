package com.stu.helloserver.service;

import com.stu.helloserver.model.entity.DocumentChunk;
import java.util.List;

public interface ChunkService {
    // 切块：文档ID、内容、块大小、重叠长度
    List<DocumentChunk> splitDocument(Long documentId, String content, int chunkSize, int overlap);
}

