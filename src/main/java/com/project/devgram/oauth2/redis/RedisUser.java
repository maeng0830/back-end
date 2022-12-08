package com.project.devgram.oauth2.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@Setter
@RedisHash(value="redisUser")
public class RedisUser {

    @Id
    private String id;

    private String type;
    private String token;

    @TimeToLive
    private Long period;

    @Builder
    public RedisUser(String id,String type,String token, Long period){
        this.id=id;
        this.type=type;
        this.token=token;
        this.period=period;
    }



}
