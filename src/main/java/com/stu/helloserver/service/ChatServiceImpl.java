package com.stu.helloserver.service;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.stu.helloserver.model.dto.ChatRequestDTO;
import com.stu.helloserver.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.stu.helloserver.model.vo.ChatResponseVO;

import java.util.List;
import java.util.UUID;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;
    private final StringRedisTemplate stringRedisTemplate;
    
    /**
     * 历史轮数控制，默认保留最近3轮对话
     */
    @Value("${chat.history.max-records:3}")
    private int maxHistoryRecords;

    public ChatServiceImpl(ChatClient.Builder chatClientBuilder, StringRedisTemplate stringRedisTemplate){
        this.chatClient = chatClientBuilder
                .defaultSystem("你是一名专业、友好、简洁的中文智能助手，请根据用户问题准确回答。")
                .defaultOptions(
                        DashScopeChatOptions.builder()
                                .withTopP(0.7)
                                .build()
                )
                .build();
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public ChatResponseVO chat(ChatRequestDTO requestDTO){
        // sessionId为空时的校验，生成唯一sessionId
        String sessionId = requestDTO.getSessionId();
        if (sessionId == null || sessionId.isBlank()) {
            sessionId = generateSessionId();
        }
        
        String message = requestDTO.getMessage();
        // 消息内容校验
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("消息内容不能为空");
        }

        String redisKey = "chat:session:" + sessionId;

        try {
            // 读取历史聊天记录（只取最近maxHistoryRecords轮）
            List<String> records = stringRedisTemplate.opsForList().range(redisKey, -(maxHistoryRecords * 2), -1);
            String historyText = "";
            if (records != null && !records.isEmpty()){
                historyText = String.join("\n", records);
            }
            
            String finalPrompt = """
                    以下是历史对话：
                    %s
                    
                    当前用户问题：
                    %s
                    """.formatted(historyText, message);
            
            String answer = chatClient.prompt(finalPrompt)
                    .call()
                    .content();
            
            // 记录本次对话到Redis
            String recordText = "用户:" + message + "\n助手:" + answer;
            stringRedisTemplate.opsForList().rightPush(redisKey, recordText);
            
            // 历史轮数控制策略：只保留最近maxHistoryRecords轮
            Long size = stringRedisTemplate.opsForList().size(redisKey);
            if (size != null && size > maxHistoryRecords) {
                stringRedisTemplate.opsForList().trim(redisKey, size - maxHistoryRecords, size - 1);
            }
            
            return new ChatResponseVO(sessionId, message, answer);
            
        } catch (Exception e) {
            // 异常处理：记录日志并返回友好错误信息
            throw new RuntimeException("聊天服务异常: " + e.getMessage(), e);
        }
    }

    /**
     * 生成唯一的sessionId
     */
    private String generateSessionId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }
}
