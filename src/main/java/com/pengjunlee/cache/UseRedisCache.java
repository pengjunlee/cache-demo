package com.pengjunlee.cache;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author pengjunlee
 * @create 2020-11-23 16:48
 */
public class UseRedisCache extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Boolean useRedis = context.getEnvironment().getProperty("cache.use-redis", Boolean.class, false);
        ConditionOutcome outCome = new ConditionOutcome(useRedis, "cache.use-redis=" + useRedis);
        return outCome;
    }
}
