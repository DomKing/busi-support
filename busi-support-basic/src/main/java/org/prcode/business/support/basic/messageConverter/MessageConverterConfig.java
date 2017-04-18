package org.prcode.business.support.basic.messageConverter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import javax.annotation.Resource;

/**
 * @ClassName: MessageConverterConfig
 * @Date: 2017-03-30 20:58
 * @Auther: kangduo
 * @Description: (消息转换器, 去除返回数据中的null)
 */
@Configuration
public class MessageConverterConfig {

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(objectMapper);
        return messageConverter;
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        setMySerializer(template);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public CustomerRedisCacheManager cacheManager() {
        CustomerRedisCacheManager redisCacheManager = new CustomerRedisCacheManager(redisTemplate());
        redisCacheManager.setUsePrefix(true);
        return redisCacheManager;
    }

    /**
     * 设置序列化方法
     */
    private void setMySerializer(RedisTemplate<String, Object> template) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        template.setKeySerializer(template.getStringSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
    }
}
