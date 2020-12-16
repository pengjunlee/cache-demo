package com.pengjunlee.ratelimit;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author pengjunlee
 * @create 2020-12-15 16:29
 */
@Service
public class RateLimitClient {
    public static final String RATE_LIMIT_KEY = "ratelimit:";

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Qualifier("getRedisScript")
    @Resource
    RedisScript<Long> ratelimitLua;


    /**
     * 获得key操作
     *
     * @param key
     * @return
     */
    public Token accquireToken(String key) {
        return accquireToken(key, 1);
    }

    public Token accquireToken(String key, Integer permits) {
        Token token = Token.SUCCESS;
        Long currMillSecond = stringRedisTemplate.execute(
                (RedisCallback<Long>) redisConnection -> redisConnection.time()
        );

        Long accquire = stringRedisTemplate.execute(ratelimitLua,
                Collections.singletonList(getKey(key)), currMillSecond.toString(), permits.toString());
        if (accquire == 1) {
            token = Token.SUCCESS;
        } else {
            token = Token.FAILED;
        }
        return token;
    }

    public String getKey(String key) {
        return RATE_LIMIT_KEY + key;
    }
}
