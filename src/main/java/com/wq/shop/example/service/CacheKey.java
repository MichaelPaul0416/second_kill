package com.wq.shop.example.service;

public enum CacheKey {
    HASH_SALT_KEY("kill-second-hash"),

    LIMIT_SALT_KEY("kill-second-limit");

    private String key;

    public String getKey() {
        return key;
    }

    CacheKey(String key){
        this.key = key;
    }
}
