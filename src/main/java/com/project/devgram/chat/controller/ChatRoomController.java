package com.project.devgram.chat.controller;

import com.project.devgram.chat.model.ChatMessage;
import com.project.devgram.chat.model.ChatRoom;
import com.project.devgram.chat.model.LoginInfo;
import com.project.devgram.chat.model.ResponseList;
import com.project.devgram.chat.repository.ChatRoomRepository;
import com.project.devgram.oauth2.token.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/api/chat")
public class ChatRoomController {


    private final ChatRoomRepository chatRoomRepository;
    private final TokenService tokenService;


    @GetMapping("/room")
    public String rooms() {
        return "/chat/room";
    }

    //유저 수를 확인 가능한 /rooms
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAllRoom();
        chatRooms.stream().forEach(room -> room.setUserCount(chatRoomRepository.getUserCount(room.getRoomId())));
        return chatRooms;
    }


    //채팅방 생성 post
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatRoomRepository.createChatRoom(name);
    }


    // roomId 이름으로 roomId 반환 및 chats 란 이름으로 chatList 반환
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        List<ChatMessage> messages = chatRoomRepository.getMessages(roomId);
        for (ChatMessage me: messages
        ) {
            log.info("messagesSender: {}",me.getSender());
            log.info("messages: {}",me.getMessage());
            log.info("messages: {}",me.getUserCount());
        }
        model.addAttribute("chats",messages);
        return "/chat/roomdetail";
    }

    // 혹시나해서 json 형식으로 채팅내역 list return
    @GetMapping("/room/enter/getMessage/{roomId}")
    @ResponseBody
    public ResponseList roomMessages(@PathVariable String roomId){

        return new ResponseList(roomId,chatRoomRepository.getMessages(roomId));
    }


    //채팅방 id를 통해 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }

    // 유저 정보 connection시 필요.
    // 인터셉터에서 validation 후 username을 redis에 저장
    @GetMapping("/user")
    @ResponseBody
    public LoginInfo getUserInfo(HttpServletRequest request) {

        String token= request.getHeader("Authentication");
        String name= tokenService.getUsername(token);


        return LoginInfo.builder().name(name).token(token).build();
    }


}
