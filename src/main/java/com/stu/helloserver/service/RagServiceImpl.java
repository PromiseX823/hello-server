package com.stu.helloserver.service;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stu.helloserver.mapper.DocumentChunkMapper;
import com.stu.helloserver.model.dto.RagQueryDTO;
import com.stu.helloserver.model.entity.DocumentChunk;
import com.stu.helloserver.model.vo.RagAnswerVO;
import com.stu.helloserver.service.RagService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RagServiceImpl implements RagService {

    private final DocumentChunkMapper documentChunkMapper;
    private final ChatClient chatClient;

    public RagServiceImpl(DocumentChunkMapper documentChunkMapper,
                          ChatClient.Builder chatClientBuilder) {
        this.documentChunkMapper = documentChunkMapper;
        this.chatClient = chatClientBuilder
                .defaultSystem("你是一名法条问答助手，请严格基于提供的材料回答。不要编造内容，回答简洁准确。")
                .defaultOptions(DashScopeChatOptions.builder()
                        .withTopP(0.7)
                        .build())
                .build();
    }

    @Override
    public RagAnswerVO query(RagQueryDTO queryDTO) {
        String question = queryDTO.getQuestion();

        // 1. 模糊检索相关片段（取前5条）
        LambdaQueryWrapper<DocumentChunk> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(DocumentChunk::getChunkContent, question)
                .last("LIMIT 5");
        List<DocumentChunk> chunkList = documentChunkMapper.selectList(wrapper);

        // 2. 拼接上下文
        String context = chunkList.stream()
                .map(DocumentChunk::getChunkContent)
                .collect(Collectors.joining("\n---\n"));

        // 3. 构造Prompt
        String prompt = String.format("参考材料：\n%s\n\n问题：%s\n\n请基于参考材料回答：", context, question);

        // 4. 调用AI生成答案
        String answer = chatClient.prompt(prompt).call().content();

        // 5. 整理返回
        List<String> sources = chunkList.stream()
                .map(DocumentChunk::getChunkContent)
                .collect(Collectors.toList());
        return new RagAnswerVO(question, answer, sources);
    }
}
