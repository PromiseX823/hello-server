package com.stu.helloserver.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("document_chunk")
public class DocumentChunk {
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 所属文档ID */
    private Long documentId;
    /** 片段序号 */
    private Integer chunkIndex;
    /** 片段内容 */
    private String chunkContent;
    /** 创建时间 */
    private LocalDateTime createTime;
}
