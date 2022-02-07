package com.eden.web.common.config;

import com.eden.web.common.properties.RedisConfigProperties;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.Arrays;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

/**
 * redis缓存配置
 *
 * @author eden
 * @since 2019/11/25
 */

@Configuration
@EnableCaching
@EnableConfigurationProperties(RedisConfigProperties.class)
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    @ConditionalOnMissingBean
    public GenericObjectPoolConfig genericObjectPoolConfig() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxIdle(200);
        config.setMaxTotal(200);
        config.setMinIdle(20);
        config.setMaxWaitMillis(-1L);
        return config;
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisConnectionFactory lettuceConnectionFactory(GenericObjectPoolConfig poolConfig, RedisConfigProperties redisConfigProperties) {
        String[] nodeHosts = redisConfigProperties.getHosts().split(",");
        RedisClusterConfiguration configuration = new RedisClusterConfiguration(Arrays.asList(nodeHosts));
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(2))
                .poolConfig(poolConfig)
                .shutdownTimeout(Duration.ofMillis(10))
                .build();
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(configuration, clientConfiguration);
        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }

    @Bean
    @ConditionalOnMissingBean
    public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(RedisSerializer.json());
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(RedisSerializer.json());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisCacheConfiguration cacheConfiguration(RedisConfigProperties redisConfigProperties) {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .computePrefixWith(name -> redisConfigProperties.getKeyPrefix() + name)
                .entryTtl(Duration.ofMinutes(redisConfigProperties.getDefaultTTL()))
                .serializeKeysWith(fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(fromSerializer(RedisSerializer.json()));
    }

}