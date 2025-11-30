package com.aoto.grains.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName TransactionController
 * @Description
 * @Author huagu
 * @Date 2025/11/30 22:03
 * @Version 1.0
 **/
@RestController
@CrossOrigin(origins = "*") // 允许任何域名访问，解决跨域问题
public class TransactionController{
    /**
     * 1. 资金提现接口
     * 对应前端: Pages.withdraw -> endpoint: '/transaction/withdraw'
     */
    @PostMapping("/transaction/withdraw")
    public Map<String, Object> withdraw(@RequestBody Map<String, Object> payload) {
        // 1. 打印前端传来的数据，方便你调试
        System.out.println("收到提现请求: " + payload);
        
        // 模拟业务逻辑：获取前端传来的金额和币种
        String amount = (String) payload.get("amount");
        String currency = (String) payload.get("currency");
        
        // 2. 构造返回数据
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("status", "SUCCESS");
        // 告诉前端业务办理成功
        response.put("transactionId", UUID.randomUUID().toString());
        // 生成一个模拟的流水号
        response.put("message", String.format("已成功提现 %s %s", amount, currency));
        
        return response;
    }
    
    /**
     * 2. 流程申请接口
     * 对应前端: Pages.apply -> endpoint: '/process/apply'
     */
    @PostMapping("/process/apply")
    public Map<String, Object> applyProcess(@RequestBody Map<String, Object> payload) {
        System.out.println("收到流程申请: " + payload);
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("applicationId", "APP-" + System.currentTimeMillis());
        response.put("message", "申请已提交，等待审批");
        
        return response;
    }
    /**
     * 3. 数据查询接口 (注意：这里是 GET 请求)
     * 对应前端: Pages.query -> endpoint: '/data/query'
     */
    @GetMapping("/data/query")
    public Map<String, Object> queryData() {
        System.out.println("收到查询请求");
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("totalAssets", 99999.99);
        response.put("lastLogin", "2025-11-30");
        
        return response;
    }
}
