package com.lowcode.flowable.task.job;

import org.flowable.engine.RepositoryService;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @PACKAGE_NAME: com.fusiontech.lowcode.flowable.task.job
 * @NAME: DeployService
 * @USER: jack
 * @DATE: 11/22/2022
 * @PROJECT_NAME: flowable-master
 **/
@Component
public class DeployService {
    @Autowired
    ModelService modelService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    FormRepositoryService formRepositoryService;

    public void deploy(String modelName){
    }
}
