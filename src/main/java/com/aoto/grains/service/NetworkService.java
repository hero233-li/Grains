package com.aoto.grains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class NetworkService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    /**
     * 通用 GET 请求 (已修复重复请求的 bug)
     */
    public String sendGet(String url, Map<String, Object> params, Map<String, String> headerMap) {
        // 1. 拼装 URL 参数
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        if (params != null) {
            params.forEach(builder::queryParam);
        }
        
        // 2. 组装 Header
        HttpHeaders headers = new HttpHeaders();
        if (headerMap != null) {
            headerMap.forEach(headers::add);
        }
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        // 3. 发送请求
        // 【注意】你之前的代码在这里调用了 restTemplate.exchange 后，
        // 又调用了一次 restTemplate.getForObject，这会导致请求发送两次，且第二次没带 Header。
        // 下面是修复后的写法：
        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class
        );
        
        return response.getBody();
    }
    
    /**
     * 通用 POST 请求 (已增加 headerMap 参数)
     * @param url 请求地址
     * @param body 请求体 (Map 或 Pojo)
     * @param headerMap 请求头 (Cookie/Token 放在这里)
     * @param isJson true=application/json, false=application/x-www-form-urlencoded
     */
    public String sendPost(String url, Object body, Map<String, String> headerMap, boolean isJson) {
        HttpHeaders headers = new HttpHeaders();
        
        // 1. 先把用户传入的 Token / Cookie 等 Header 放进去
        if (headerMap != null) {
            headerMap.forEach(headers::add);
        }
        
        // 2. 设置 Content-Type (会覆盖 headerMap 里可能存在的 Content-Type，保证准确)
        if (isJson) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        } else {
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }
        
        // 3. 组装请求实体
        HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);
        
        // 4. 发送 POST 请求
        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, String.class
        );
        return response.getBody();
    }
}
