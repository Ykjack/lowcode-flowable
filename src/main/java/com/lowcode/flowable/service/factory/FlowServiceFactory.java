package com.lowcode.flowable.service.factory;

import lombok.Getter;
import org.flowable.engine.*;
import org.flowable.form.api.FormService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @PACKAGE_NAME: com.lowcode.flowable.service.factory
 * @NAME: FlowServiceFactory
 * @USER: jack
 * @DATE: 11/24/2022
 * @PROJECT_NAME: flowable-master
 *
 * 将所有流程引擎中用到的集成
 **/
@Component
@Getter
public class FlowServiceFactory {
    @Resource
    protected RepositoryService repositoryService;

    @Resource
    protected RuntimeService runtimeService;

    @Resource
    protected IdentityService identityService;

    @Resource
    protected TaskService taskService;

    @Resource
    protected FormService formService;

    @Resource
    protected HistoryService historyService;

    @Resource
    protected ManagementService managementService;

    @Qualifier("processEngine")
    @Resource
    protected ProcessEngine processEngine;
}
