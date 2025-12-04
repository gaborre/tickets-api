package com.gabn.tickets.configs;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

import static com.gabn.tickets.constants.CachingConstants.TICKETS_API_CACHE_TAG;

@EnableCaching
@Configuration
public class CacheConfig {
    @Value("${cache.tickets.ttl-in-minutes}")
    private int ttlInMinutes;

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine
            .newBuilder()
            .expireAfterWrite(ttlInMinutes, TimeUnit.MINUTES);
    }

    @Bean
    @Primary
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.getCache(TICKETS_API_CACHE_TAG);
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }
}
