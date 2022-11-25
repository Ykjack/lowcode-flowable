package com.lowcode.flowable.controller;

import com.lowcode.flowable.service.IProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mayike
 * @Date: 2022/11/21 14:58
 */
@RestController
@RequestMapping("/proc")
public class ProcessController {
    @Autowired
    IProcessService processService;

    @GetMapping("/test")
    public Object start() {
        //processService.start();
        //String modelKey, String initiator, String department, Map<String, Object> variables
        String modelKey = "demo";
        String initiator = "admin";
        Map<String, Object> variables = new HashMap<>();
        variables.put("projType","1");
        processService.start(modelKey,initiator,null,variables);
        return null;
    }
}
