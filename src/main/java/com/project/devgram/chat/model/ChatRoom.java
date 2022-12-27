package com.project.devgram.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;


    private String roomId;
    private String name;
    private long userCount;

    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        String str =  UUID.randomUUID().toString().replaceAll("-","");
        chatRoom.roomId =str.substring(0,12);
        chatRoom.name = name;

        return chatRoom;
    }
}
