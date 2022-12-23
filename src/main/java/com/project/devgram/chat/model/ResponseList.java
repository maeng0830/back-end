package com.project.devgram.chat.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseList {


    private final String roomId;
    private final List<ChatMessage> chatMessageList;


    @Builder
    public ResponseList(String roomId, List<ChatMessage> messages) {
        this.roomId = roomId;
        this.chatMessageList=messages;
    }
}
