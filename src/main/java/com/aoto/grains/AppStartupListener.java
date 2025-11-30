package com.aoto.grains;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName AppStartuoListener
 * @Description
 * @Author huagu
 * @Date 2025/11/30 21:35
 * @Version 1.0
 **/
@Component
public class AppStartupListener implements ApplicationListener<WebServerInitializedEvent>{
    private static final Logger log = LoggerFactory.getLogger(AppStartupListener.class);
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        int port = event.getWebServer().getPort();
        String contextPath = event.getApplicationContext().getApplicationName();
        String hostAddress = "localhost";
        try{
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        }catch(UnknownHostException e){
            log.warn("无法获取本机局域网IP，将使用默认值");
        }
        //打印常用的的访问地址
        log.info("""
                
                ----------------------------------------------------------
                \t项目启动成功！访问地址如下:
                \t本机访问 (Local): \thttp://127.0.0.1:{}{}
                \t内网访问 (Network): \thttp://{}:{}{}
                ----------------------------------------------------------
                """,
                port,contextPath,
                hostAddress,port,contextPath);
        
    }
}
