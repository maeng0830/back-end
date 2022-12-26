package com.project.devgram.chat.model;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class ResponseList implements Serializable {

    private static final long serialVersionUID = 649897752089006639L;
    private final String roomId;
    private final List<ChatMessage> chatMessageList;


    @Builder
    public ResponseList(String roomId, List<ChatMessage> messages) {
        this.roomId = roomId;
        this.chatMessageList=messages;
    }
}
