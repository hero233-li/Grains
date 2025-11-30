package com.aoto.grains.model.dto;

import lombok.Data;

/**
 * @ClassName SearchReq
 * @Description
 * @Author huagu
 * @Date 2025/11/30 22:53
 * @Version 1.0
 **/
@Data
public class SearchReq {
    // 对应前端的 keyword 字段
    private String keyword;
    
    // 如果有其他参数，继续加字段即可
    // private int page;
}
