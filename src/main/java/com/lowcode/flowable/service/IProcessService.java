package com.lowcode.flowable.service;

import java.util.Map;

/**
 * @Author: mayike
 * @Date: 2022/11/21 14:58
 */
public interface IProcessService {

    /**
     * 开始一个流程
     * @param modelKey
     * @param initiator
     * @param department
     * @param variables
     * @return
     */
    void start(String modelKey, String initiator, String department, Map<String, Object> variables);

    String start2(String modelKey, String initiator, Map<String, Object> variables);
}
