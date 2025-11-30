package com.aoto.grains;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ApiController
 * @Description
 * @Author huagu
 * @Date 2025/11/30 22:37
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // 允许前端跨域调用
public class ApiController{
    @Autowired
    private NetworkService networkService;
    @Autowired
    private ObjectMapper objectMapper;
    @PostMapping("/chain-search")
    public Map<String, Object> chainSearch(@RequestBody Map<String, String> payload) {
        Map<String,Object> result=new HashMap<>();
        // 1. 获取前端想搜的词，比如 "卫衣"
        String keyword = payload.getOrDefault("keyword", "卫衣");
        System.out.println("开始执行链式调用，关键词: " + keyword);
        try {
            // ==========================================
            // 第一步：调用淘宝 Suggest API
            // ==========================================
            String taobaoUrl = "https://suggest.taobao.com/sug";
            Map<String, Object> taobaoParams = new HashMap<>();
            taobaoParams.put("code", "utf-8");
            taobaoParams.put("q", keyword);
            // 注意：我不传 callback 参数，这样淘宝会返回纯 JSON
            
            String taobaoResStr = networkService.sendGet(taobaoUrl, taobaoParams);
            System.out.println("淘宝返回: " + taobaoResStr);
            
            // 解析淘宝的数据，假设我们要拿到第一个推荐词
            JsonNode taobaoJson = objectMapper.readTree(taobaoResStr);
            JsonNode resultNode = taobaoJson.get("result");
            
            String nextKeyword = keyword; // 默认如果没找到推荐词，还是用原词
            if (resultNode.isArray() && resultNode.size() > 0) {
                // 淘宝返回结构是 [[词, 编号], [词, 编号]...]
                // 我们取第一个推荐词作为百度的搜索词
                nextKeyword = resultNode.get(0).get(0).asText();
                System.out.println("从淘宝获取到的推荐词: " + nextKeyword);
            }
            
            // ==========================================
            // 第二步：调用百度百科 API
            // ==========================================
            // https://baike.baidu.com/api/openapi/BaikeLemmaCardApi?scope=103&format=json&appid=379020&bk_key=银魂&bk_length=600
            String baiduUrl = "https://baike.baidu.com/api/openapi/BaikeLemmaCardApi";
            Map<String, Object> baiduParams = new HashMap<>();
            baiduParams.put("scope", "103");
            baiduParams.put("format", "json");
            baiduParams.put("appid", "379020");
            baiduParams.put("bk_key", nextKeyword); // 使用淘宝的结果
            baiduParams.put("bk_length", "600");
            
            String baiduResStr = networkService.sendGet(baiduUrl, baiduParams);
            System.out.println("百度返回: " + baiduResStr);
            
            // 解析百度数据
            JsonNode baiduJson = objectMapper.readTree(baiduResStr);
            
            // ==========================================
            // 第三步：组装最终结果给前端
            // ==========================================
            result.put("code", 200);
            result.put("originalKeyword", keyword);
            result.put("taobaoRecommend", nextKeyword);
            result.put("baiduWiki", baiduJson); // 直接把百度的 JSON 塞进去
            
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("error", e.getMessage());
        }
        
        return result;
    }
    
}
