package com.stu.helloserver.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("knowledge_document")
public class KnowledgeDocument {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 原始文件名 */
    private String fileName;

    /** 文件类型：txt/pdf */
    private String fileType;

    /** 解析后的纯文本内容 */
    private String content;

    /** 创建时间 */
    private LocalDateTime createTime;
}
