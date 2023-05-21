package one.terenin.config.base;

import lombok.RequiredArgsConstructor;
import one.terenin.config.base.propertysource.CachingPropertySource;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final CachingPropertySource cachingPropertySource;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        return new JedisConnectionFactory(redisStandaloneConfiguration());
    }

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration(){
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(cachingPropertySource.getHost());
        configuration.setPort(Integer.parseInt(cachingPropertySource.getPort()));
        //configuration.setUsername(cachingPropertySource.getUsername());
        //configuration.setPassword(cachingPropertySource.getPassword());
        return configuration;
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration(){
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(1))
                .disableCachingNullValues();
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory,
                                     RedisCacheConfiguration configuration){
        return RedisCacheManager.builder(factory)
                .cacheDefaults(configuration)
                .build();
    }

}
