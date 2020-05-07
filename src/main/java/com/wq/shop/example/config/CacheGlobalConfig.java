package com.wq.shop.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: wangqiang20995
 * @Date: 2020/5/6 16:18
 * @Description:
 **/

@Configuration
public class CacheGlobalConfig {

    @Value("${cache.key.expire.time}")
    private int expireCacheKey;

    public int getExpireCacheKey() {
        return expireCacheKey;
    }

    public void setExpireCacheKey(int expireCacheKey) {
        this.expireCacheKey = expireCacheKey;
    }
}
