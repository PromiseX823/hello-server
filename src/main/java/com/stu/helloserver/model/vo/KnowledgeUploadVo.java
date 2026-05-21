package com.stu.helloserver.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeUploadVo {
    private String fileName;
    private String fileType;
    private Integer contentLength;
    private Long documentId;
}
