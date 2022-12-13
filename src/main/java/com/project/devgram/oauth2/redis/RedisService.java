package com.project.devgram.oauth2.redis;

import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.TokenErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.project.devgram.type.TokenType.ATK;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisService {

    private final TokenRedisRepository tokenRedisRepository;


    public void createRefresh(String id,String token,String type,Long period){

        log.info("username {} ",id);

        RedisUser user = RedisUser.builder()
                .id(id)
                .token(token)
                .type(type)
                .period(period)
                .build();


        tokenRedisRepository.save(user);
    }
    @Transactional
    public boolean deleteRefresh(String id){
        log.info("delete RefreshToken");
        Optional<RedisUser> targetToken = tokenRedisRepository.findById(id);

        if(targetToken.isPresent()){

            RedisUser target = targetToken.get();

            tokenRedisRepository.delete(target);
            return true;
        }
          return false;
        }



    public String getRefreshToken(String id) {

      RedisUser redis = tokenRedisRepository.findById(id)
              .orElseThrow(() -> new DevGramException(TokenErrorCode.NOT_EXIST_TOKEN));

          return redis.getId();
      }

      //blackList 추가
    @Transactional
    public void blackListPush(String token) {

        RedisUser user = RedisUser.builder()
                .id(token)
                .type(String.valueOf(ATK))
                .period(300L)
                .build();

        tokenRedisRepository.save(user);
    }

    public boolean getBlackToken(String id) {

        Optional<RedisUser> targetToken =tokenRedisRepository.findById(id);

        if(targetToken.isEmpty()){
            // 로그인 가능
            return false;
        }
        // 로그인 실패
        return true;
    }
}
