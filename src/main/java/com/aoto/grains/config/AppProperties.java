package com.aoto.grains.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName AppProperties
 * @Description
 * @Author huagu
 * @Date 2025/11/30 23:55
 * @Version 1.0
 **/
@Data
@Component
@ConfigurationProperties(prefix = "app") // 自动读取 yml 中 app 开头的配置
public class AppProperties{
    // 自动对应 app.taobao-url
    private String taobaoUrl;
    
    // 自动对应 app.baidu-url
    private String baiduUrl;
    
    // 自动对应 app.timeout (接口超时时间)
    private Integer timeout;
    
    // 自动对应 app.user-agent
    private String userAgent;
}
