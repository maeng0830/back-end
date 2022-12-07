package com.project.devgram.oauth2.redis;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRedisRepository extends CrudRepository<RedisUser,String> {

    Optional<RedisUser> findByRedisUserId(String username);
}

