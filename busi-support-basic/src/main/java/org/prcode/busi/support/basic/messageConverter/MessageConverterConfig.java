package org.prcode.busi.support.basic.messageConverter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        messageConverter.setObjectMapper(objectMapper);
        return messageConverter;
    }
}
