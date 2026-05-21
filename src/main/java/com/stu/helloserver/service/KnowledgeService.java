package com.stu.helloserver.service;

import com.stu.helloserver.model.vo.KnowledgeUploadVo;
import org.springframework.web.multipart.MultipartFile;

public interface KnowledgeService {
    KnowledgeUploadVo upload(MultipartFile file);
}
