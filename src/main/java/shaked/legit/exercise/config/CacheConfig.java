package shaked.legit.exercise.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

import static shaked.legit.exercise.constants.PropertiesNames.ABNORMAL_REPOSITORY_LIVETIME;
import static shaked.legit.exercise.constants.PropertiesNames.REPOSITORY_CACHE_MAX_SIZE;

@Configuration
public class CacheConfig {

    @Bean
    public CacheLoader<String, String> cacheLoader() {
        return new CacheLoader<>() {
            @Override
            public String load(String key) {
                return key;
            }
        };
    }

    @Bean
    public LoadingCache<String, String> loadingCache(CacheLoader<String, String> loader,
                                                     @Value("${" + REPOSITORY_CACHE_MAX_SIZE + "}") Integer maxSize,
                                                     @Value("${" + ABNORMAL_REPOSITORY_LIVETIME + "}") Integer cacheLiveTime) {
        return CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(cacheLiveTime, TimeUnit.MINUTES)
                .build(loader);
    }



}
