package com.aoto.grains.service.impl;

import com.aoto.grains.config.AppProperties;
import com.aoto.grains.service.NetworkService;
import com.aoto.grains.service.SearchService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SearchServiceImpl
 * @Description
 * @Author huagu
 * @Date 2025/11/30 22:54
 * @Version 1.0
 **/
@Slf4j // Lombok 自动生成 log 对象
@Service
@RequiredArgsConstructor // Lombok 自动生成构造函数注入，替代 @Autowired
public class SearchServiceImpl implements SearchService{
    private final NetworkService networkService;
    private final ObjectMapper objectMapper;
    private final AppProperties appProperties;
    // ✅ 注入配置类
    @Override
    public JsonNode chainSearch(String keyword) {
        try {
            log.info("开始搜索: {}", keyword);
            
            // 1. 淘宝
            String url = appProperties.getTaobaoUrl();
            Map<String, Object> taobaoParams = new HashMap<>();
            taobaoParams.put("code", "utf-8");
            taobaoParams.put("q", keyword);
            String taobaoRes = networkService.sendGet(url, headers,taobaoParams);
            
            // 解析淘宝
            JsonNode taobaoJson = objectMapper.readTree(taobaoRes);
            JsonNode resultNode = taobaoJson.get("result");
            String nextKeyword = keyword;
            if (resultNode.isArray() && resultNode.size() > 0) {
                nextKeyword = resultNode.get(0).get(0).asText();
            }
            // 2. 百度
            String baiduUrl = "https://baike.baidu.com/api/openapi/BaikeLemmaCardApi";
            Map<String, Object> baiduParams = new HashMap<>();
            baiduParams.put("scope", "103");
            baiduParams.put("format", "json");
            baiduParams.put("appid", "379020");
            baiduParams.put("bk_key", nextKeyword);
            baiduParams.put("bk_length", "600");
            
            String baiduRes = networkService.sendGet(baiduUrl, baiduParams);
            
            return objectMapper.readTree(baiduRes);
            
        } catch (Exception e) {
            log.error("搜索链路异常", e);
            throw new RuntimeException("外部接口调用失败: " + e.getMessage());
        }
    }
}
