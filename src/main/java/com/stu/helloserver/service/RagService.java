package com.stu.helloserver.service;

import com.stu.helloserver.model.dto.RagQueryDTO;
import com.stu.helloserver.model.vo.RagAnswerVO;

public interface RagService {
    RagAnswerVO query(RagQueryDTO queryDTO);
}
