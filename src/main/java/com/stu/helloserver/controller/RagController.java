package com.stu.helloserver.controller;

import com.stu.helloserver.common.Result;
import com.stu.helloserver.model.dto.RagQueryDTO;
import com.stu.helloserver.model.vo.RagAnswerVO;
import com.stu.helloserver.service.RagService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rag")
public class RagController {

    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping("/query")
    public Result<RagAnswerVO> query(@RequestBody RagQueryDTO queryDTO) {
        RagAnswerVO vo = ragService.query(queryDTO);
        return Result.success(vo);
    }
}
