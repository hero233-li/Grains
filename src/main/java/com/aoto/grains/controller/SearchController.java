package com.aoto.grains.controller;


import com.aoto.grains.common.Result;
import com.aoto.grains.model.dto.SearchReq;
import com.aoto.grains.service.SearchService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
@CrossOrigin(origins = "*") //
@RequiredArgsConstructor
public class SearchController{
    private final SearchService searchService;
    @PostMapping("/chain-search")
    // 参数改成 SearchReq 类，返回改成 Result 统一泛型
    public Result<Object> chainSearch(@RequestBody SearchReq req) {
        // 使用默认值逻辑
        String keyword = (req.getKeyword() == null || req.getKeyword().isEmpty()) ? "卫衣" : req.getKeyword();
        
        try {
            // Controller 只负责调用，不管具体怎么调淘宝百度
            Object data = searchService.chainSearch(keyword);
            return Result.success(data);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    
    
}
