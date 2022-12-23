package com.project.devgram.chat.repository;

import com.project.devgram.chat.model.ChatMessage;
import com.project.devgram.chat.model.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Repository
public class ChatRoomRepository {
    // 채팅방(topic)에 발행되는 메시지를 처리할 Listner
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    public static final String USER_COUNT = "USER_COUNT";
    public static final String ENTER_INFO = "ENTER_INFO";

    public static final String ROOM_MESSAGES = "ROOM_MESSAGES";
    public static final String CHAT_USERS= "CHAT_USERS";


    private final RedisTemplate<String,Object> redisTemplate;



    // 모든 채팅방 조회
    public List<ChatRoom> findAllRoom() {
        HashOperations<String, String, ChatRoom> hashOpsChatRoom = redisTemplate.opsForHash();

        return hashOpsChatRoom.values(CHAT_ROOMS);
    }

    // 특정 채팅방 조회
    public ChatRoom findRoomById(String id) {
        HashOperations<String, String, ChatRoom> hashOpsChatRoom = redisTemplate.opsForHash();
        return hashOpsChatRoom.get(CHAT_ROOMS, id);
    }

    // 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        log.info("chatRoom {}",chatRoom);

        HashOperations<String, String, ChatRoom> hashOpsChatRoom = redisTemplate.opsForHash();
        hashOpsChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);

        return chatRoom;
    }

    //chating 내용 저장
    public void sendMessage(String roomId, ChatMessage message) {
        HashOperations<String,String, List<ChatMessage>> roomMessages =redisTemplate.opsForHash();
        List<ChatMessage> messages = roomMessages.get(ROOM_MESSAGES, roomId);
        if(messages == null) messages = new ArrayList<>();

        messages.add(message);
        roomMessages.put(ROOM_MESSAGES, roomId, messages);
    }
    //chating 내용 가져오기
    public List<ChatMessage> getMessages(String roomId) {
        HashOperations<String,String, List<ChatMessage>> roomMessages =redisTemplate.opsForHash();
        List<ChatMessage> messages = roomMessages.get(ROOM_MESSAGES, roomId);
        if(messages == null) return new ArrayList<>();
        return messages;
    }

    // 유저가 입장한 채팅방ID와 유저 세션ID 맵핑 정보 저장
    public void setUserEnterInfo(String sessionId, String roomId) {
        HashOperations<String, String, String> hashOpsEnterInfo =redisTemplate.opsForHash();
        log.info("sessionId {}",sessionId);
        hashOpsEnterInfo.put(ENTER_INFO, sessionId, roomId);
    }

    public void setUsernameInfo(String sessionId,String token){
        HashOperations<String, String, String> hashOpsEnterInfo =redisTemplate.opsForHash();
        log.info("해당 token set : {} ",token);
        hashOpsEnterInfo.put(CHAT_USERS,sessionId,token);
    }

    public String getTokenInfo(String sessionId){
        HashOperations<String, String, String> hashOpsEnterInfo =redisTemplate.opsForHash();
        log.info("get sessionid {}",sessionId);
         return hashOpsEnterInfo.get(CHAT_USERS,sessionId);
    }

    // 유저 세션으로 입장해 있는 채팅방 ID 조회
    public String getUserEnterRoomId(String sessionId) {
        HashOperations<String, String, String> hashOpsEnterInfo =redisTemplate.opsForHash();
        return hashOpsEnterInfo.get(ENTER_INFO, sessionId);
    }

    // 유저 세션정보와 맵핑된 채팅방ID 삭제
    public void removeUserEnterInfo(String sessionId) {
        HashOperations<String, String, String> hashOpsEnterInfo =redisTemplate.opsForHash();
        hashOpsEnterInfo.delete(ENTER_INFO, sessionId);
    }

    // 채팅방 유저수 조회
    public long getUserCount(String roomId) {
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        return Long.parseLong((String) Optional.ofNullable(valueOps.get(USER_COUNT + "_" + roomId)).orElse("0"));
    }

    // 채팅방에 입장한 유저수 +1
    public long plusUserCount(String roomId) {
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        return Optional.ofNullable(valueOps.increment(USER_COUNT + "_" + roomId)).orElse(0L);
    }

    // 채팅방에 입장한 유저수 -1
    public long minusUserCount(String roomId) {
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        return Optional.ofNullable(valueOps.decrement(USER_COUNT + "_" + roomId)).filter(count -> count > 0).orElse(0L);
    }

    public void deleteById(String sessionId,String roomId) {
        log.info("해당 room은 삭제되었습니다. {} ",roomId);

        HashOperations<String, String, ChatRoom> hashOpsChatRoom=redisTemplate.opsForHash();
        hashOpsChatRoom.delete(CHAT_ROOMS,roomId);

        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(sessionId,roomId);

        HashOperations<String,String, List<ChatMessage>>roomMessages =redisTemplate.opsForHash();
        roomMessages.delete(ROOM_MESSAGES, roomId);

        redisTemplate.opsForValue().set(USER_COUNT + "_" + roomId,"0", Duration.ofSeconds(15L));
    }

}