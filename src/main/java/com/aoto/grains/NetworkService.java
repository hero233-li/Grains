package com.aoto.grains;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * @ClassName NetworkService
 * @Description
 * @Author huagu
 * @Date 2025/11/30 22:31
 * @Version 1.0
 **/
@Service
public class NetworkService{
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 通用GET请求
     */
    public String sendGet(String url, Map<String,Object> params){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        if(params!=null){
            params.forEach(builder::queryParam);
        }
        //发送请求，返回String类型的原始数据
        return restTemplate.getForObject(builder.toUriString(), String.class);
    }
    /**
     * 通用 POST 请求 (处理 JSON 或 Form)
     * @param isJson true=发送JSON, false=发送表单
     */
    public String sendPost(String url, Object body, boolean isJson) {
        HttpHeaders headers = new HttpHeaders();
        if (isJson) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        } else {
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }
        
        HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);
        
        // 发送 POST 请求
        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, String.class
        );
        return response.getBody();
    }
}
