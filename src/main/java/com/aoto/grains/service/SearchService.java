package com.aoto.grains.service;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author huagu
 */
public interface SearchService {
    // 定义业务接口
    JsonNode chainSearch(String keyword);
}
