package com.pengjunlee.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * @author pengjunlee
 * @create 2020-11-23 16:30
 */
@Configuration
@EnableCaching
@Slf4j
public class CacheConfig extends CachingConfigurerSupport {

    /**
     * 添加自定义缓存异常处理
     * 当缓存读写异常时,忽略异常
     */
    @Bean
    @Override
    public CacheErrorHandler errorHandler() {

        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache,
                                            Object key, Object value) {
                redisErrorException(exception, key);
            }

            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache,
                                            Object key) {
                redisErrorException(exception, key);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache,
                                              Object key) {
                redisErrorException(exception, key);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                redisErrorException(exception, null);
            }
        };
        return cacheErrorHandler;
    }

    protected void redisErrorException(Exception exception, Object key) {
        log.error("缓存异常：key=[{}]", key, exception);
    }

    /**
     * 基于SpringBoot2 对 RedisCacheManager 的自定义配置
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean(name = "redisCacheManager")
    @Primary
    @Conditional(UseRedisCache.class)
    // @ConditionalOnProperty(name = "cache.use-redis", havingValue = "true", matchIfMissing = false)
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 构造一个redis缓存管理器
        RedisCacheManager redisCacheManager =
                RedisCacheManager.RedisCacheManagerBuilder
                        .fromCacheWriter(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                        // Redis 连接工厂
                        //.fromConnectionFactory(redisConnectionFactory)
                        // 缓存配置
                        .cacheDefaults(getCacheConfigurationWithTtl(10L))
                        .withCacheConfiguration("dept_cache", getCacheConfigurationWithTtl(60L))
                        // 配置同步修改或删除 put/evict
                        .transactionAware()
                        .build();
        return redisCacheManager;
    }

    private RedisCacheConfiguration getCacheConfigurationWithTtl(Long seconds) {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                // 设置key为String
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // 设置value 为自动转Json的Object
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                // 缓存数据保存1小时
                .entryTtl(Duration.ofSeconds(seconds))
                // 不缓存null
                .disableCachingNullValues();
    }


    /*
     * ehcache 主要的管理器
     */
    @Bean(name = "ehCacheCacheManager")
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean) {
        return new EhCacheCacheManager(bean.getObject());
    }

    /*
     * 据shared与否的设置,Spring分别通过CacheManager.create()或new CacheManager()方式来创建一个ehcache基地.
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("Ehcache.xml"));
        cacheManagerFactoryBean.setShared(false);
        return cacheManagerFactoryBean;
    }

    /**
     * 自定义部门缓存Key生成策略
     *
     * @return
     */
    @Bean(name = "deptKeyGenerator")
    public KeyGenerator deptKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... params) {
                // String key = o.getClass().getSimpleName() + "dept:" + Arrays.asList(params);
                String key = "dept|" + params[0];
                return key;
            }
        };
    }
}
