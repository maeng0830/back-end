package com.project.devgram.chat.controller;


import com.project.devgram.chat.model.ChatMessage;
import com.project.devgram.chat.repository.ChatRoomRepository;
import com.project.devgram.chat.service.ChatService;
import com.project.devgram.oauth2.token.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

  private final TokenService tokenService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;


    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/api/chat/message")
    public void message(ChatMessage message, @Header("token") String token) {
        String nickname = tokenService.getUsername(token);

        // 로그인 회원 정보로 대화명 설정
        message.setSender(nickname);

        message.setCurrentTime(LocalDateTime.now());

        // 채팅방 인원수 세팅
        message.setUserCount(chatRoomRepository.getUserCount(message.getRoomId()));

        // Websocket에 발행된 메시지를 redis로 발행(publish)
        log.info("message content: "+ message.getMessage());
        log.info("message time: {}",message.getCurrentTime());

        chatRoomRepository.sendMessage(message.getRoomId(),message);

        chatService.sendChatMessage(message);
    }


}