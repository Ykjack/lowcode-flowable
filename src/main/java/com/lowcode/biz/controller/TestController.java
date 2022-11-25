package com.lowcode.biz.controller;

import com.lowcode.biz.common.config.LowcodeCurrentProvider;
import com.lowcode.biz.entity.User;
import com.lowcode.flowable.common.entity.AjaxResult;
import com.lowcode.flowable.service.IProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.ui.common.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @PACKAGE_NAME: com.lowcode.biz.controller
 * @NAME: TestController
 * @USER: jack
 * @DATE: 11/24/2022
 * @PROJECT_NAME: flowable-master
 **/
@RestController
@RequestMapping("/demo")
@Slf4j
@Api(tags = "测试类")
public class TestController {
    private static Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private LowcodeCurrentProvider lowcodeCurrentProvider;

    @Autowired
    private IProcessService iProcessService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/start")
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "username", value = "用户名")})
    public AjaxResult startPro(){
        //User sysUser = lowcodeCurrentProvider.getSysUser();
        ///String name = sysUser.getName();
        String proInstallId = iProcessService.start2("pro_Test", "13419645803", new HashMap<>());
        return  AjaxResult.success(proInstallId);
    }

    @GetMapping("/todoList")
    public AjaxResult todoList(){
        String currentUserId = SecurityUtils.getCurrentUserId();
        User loginUser = com.lowcode.flowable.common.utils.SecurityUtils.getLoginUser();
        TaskQuery taskQuery = taskService.createTaskQuery()
                .active()
                .includeProcessVariables()
                .taskAssignee("18672375817")
                .orderByTaskCreateTime().desc();
        List<Task> list = taskQuery.list();
        return AjaxResult.success(list);
    }
}
