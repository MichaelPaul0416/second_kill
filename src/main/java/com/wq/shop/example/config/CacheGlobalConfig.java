package com.wq.shop.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: wangqiang20995
 * @Date: 2020/5/6 16:18
 * @Description:
 **/
@Component
@ConfigurationProperties(prefix = "cache")
public class CacheGlobalConfig {

    private int expireCacheKey;

    public int getExpireCacheKey() {
        return expireCacheKey;
    }

    public void setExpireCacheKey(int expireCacheKey) {
        this.expireCacheKey = expireCacheKey;
    }
}
