package project.movie_reservation_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import project.movie_reservation_system.config.RedisConfig;

import java.security.InvalidKeyException;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String,Object> redisTemplate;

    public void set(String key, Object value){
        redisTemplate.opsForValue().set(key,value);
    }

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key){
        if(hasKey(key)){ redisTemplate.delete(key);}

    }

    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    public void setWithExpiry(String key, Object value, Duration ttl){
        redisTemplate.opsForValue().set(key,value,ttl);
    }

    public boolean setIfAbsent(String key, Object value,Duration ttl){
        return redisTemplate.opsForValue().setIfAbsent(key,value,ttl);
    }




}

