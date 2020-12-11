package com.pengjunlee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author pengjunlee
 * @create 2020-11-23 9:04
 */
@SpringBootApplication(exclude = {})
public class CacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
        System.out.println("ehcache缓存存放路径：" + System.getProperty("java.io.tmpdir"));
    }
}
