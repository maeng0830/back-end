package com.project.devgram.oauth2.redis;

import org.springframework.data.repository.CrudRepository;


public interface TokenRedisRepository extends CrudRepository<RedisUser,String>{



}

