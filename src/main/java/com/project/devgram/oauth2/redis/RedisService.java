package com.project.devgram.oauth2.redis;

import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.TokenErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisService {

    private final TokenRedisRepository tokenRedisRepository;


    public void createRefresh(String id,String token,String type,Long period){
        log.info("createRefresh");
        log.info("username {} ",id);

        RedisUser user = RedisUser.builder()
                .id(id)
                .token(token)
                .type(type)
                .period(period)
                .build();


        tokenRedisRepository.save(user);
    }

    public void deleteRefresh(String id){
        log.info("delete RefreshToken");

        RedisUser targetToken =tokenRedisRepository.findById(id)
                .orElseThrow(() -> new DevGramException(TokenErrorCode.NOT_EXIST_TOKEN));


            tokenRedisRepository.delete(targetToken);
            log.info("delete refreshToken");
        }



    public String getRefreshToken(String id) {


      RedisUser redis = tokenRedisRepository.findById(id)
              .orElseThrow(() -> new DevGramException(TokenErrorCode.NOT_EXIST_TOKEN));


          return redis.getId();
      }

}
