package com.stu.helloserver.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RagAnswerVO {
    private String question;
    private String answer;
    private List<String> sources;
}

