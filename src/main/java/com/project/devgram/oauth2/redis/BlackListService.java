package com.project.devgram.oauth2.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlackListService {

    private final RedisTemplate<String,String> redisTemplate;



    public void setAccessTokenVal(String token){
        log.info("로그인방지 AccessToken 값 삽입");

        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(token,"ATK", Duration.ofSeconds(300));

    }

    public boolean getBlackListVal(String id){

        log.info("BlackList 확인");

        Boolean keyOper = redisTemplate.hasKey(id);
        return Boolean.TRUE.equals(keyOper);
    }
}
