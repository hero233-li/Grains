package com.aoto.grains.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName AppConfig
 * @Description
 * @Author huagu
 * @Date 2025/11/30 22:30
 * @Version 1.0
 **/
@Configuration
public class AppConfig{
    @Bean
    public RestTemplate restTemplate(AppProperties appProperties){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        int timeout = (appProperties.getTimeout() != null) ? appProperties.getTimeout() : 5000;
        factory.setConnectTimeout(timeout);
        // 连接超时 5秒
        factory.setReadTimeout(5000);
        // 读取超时 5秒
        return new RestTemplate();
    }
}
