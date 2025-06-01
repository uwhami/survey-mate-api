package com.surveymate.api.domain.auth.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class GuavaCacheService {

    private final Cache<String, String> cache;
    public GuavaCacheService() {
        this.cache = CacheBuilder.newBuilder()
                .expireAfterAccess(60, TimeUnit.MINUTES) // 마지막 접근부터 60분 TTL
                .maximumSize(5000) // 최대 10만개 항목
                .build();
    }

    public void saveToCache(String uuid, String memnum) {
        cache.put(uuid, memnum);
    }

    public String getFromCache(String uuid) {
        return cache.getIfPresent(uuid);
    }

    public void removeFromCache(String uuid) {
        cache.invalidate(uuid);
    }

    @Scheduled(fixedRate = 600000) // 10분마다 실행
    public void cleanUpCache() {
        cache.cleanUp();
//        log.info("================================Cache cleaned up================================");
    }
}
