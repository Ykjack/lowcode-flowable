package com.lowcode.flowable.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lowcode.biz.entity.User;
import com.lowcode.flowable.common.constants.ProcessConstants;
import com.lowcode.flowable.common.dto.FlowProcDefDto;
import com.lowcode.flowable.common.entity.AjaxResult;
import com.lowcode.flowable.common.enums.FlowComment;
import com.lowcode.flowable.common.utils.SecurityUtils;
import com.lowcode.flowable.service.IFlowDefinitionService;
import com.lowcode.flowable.service.factory.FlowServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.lowcode.flowable.common.constants.ProcessConstants.BPMN_FILE_SUFFIX;

/**
 * @PACKAGE_NAME: com.lowcode.flowable.service.impl
 * @NAME: FlowDefinitionServiceImpl
 * @USER: jack
 * @DATE: 11/24/2022
 * @PROJECT_NAME: flowable-master
 **/
@Slf4j
@Service
public class FlowDefinitionServiceImpl extends FlowServiceFactory implements IFlowDefinitionService {
    @Override
    public boolean exist(String processDefinitionKey) {
        return repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .count() > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 流程定义列表 todo
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @return 流程定义分页列表数据
     */
    @Override
    public Page<FlowProcDefDto> list(String name, Integer pageNum, Integer pageSize) {
        Page<FlowProcDefDto> page = new Page<>();
        PageHelper.startPage(pageNum, pageSize);

        return null;
    }

    /**
     * 导入流程文件
     *
     * @param name
     * @param category
     * @param in
     */
    @Override
    public void importFile(String name, String category, InputStream in) {
        Deployment deploy = repositoryService.createDeployment().addInputStream(name + BPMN_FILE_SUFFIX, in).name(name).category(category).deploy();
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();
        repositoryService.setProcessDefinitionCategory(definition.getId(), category);
    }

    /**
     * 读取流程文件的信息
     *
     * @param deployId
     * @return
     * @throws IOException
     */
    @Override
    public AjaxResult readXml(String deployId) throws IOException {
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
        InputStream inputStream = repositoryService.getResourceAsStream(definition.getDeploymentId(), definition.getResourceName());
        String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
        return AjaxResult.success("", result);
    }

    /**
     * 根据流程定义ID启动流程实例
     *
     * @param procDefId
     * @param variables
     * @return
     */
    @Override
    public AjaxResult startProcessInstanceById(String initiator,String procDefId, Map<String, Object> variables) {
        try {
            //获取最新的流程实例
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId)
                    .latestVersion().singleResult();
            //如果流程实例未激活是不能启动的
            if (ObjectUtil.isNotNull(processDefinition) && processDefinition.isSuspended()) {
                return AjaxResult.error("流程已被挂起,请先激活流程");
            }
            //原本是应该获取登录用户的id 作为发起人的  登录人无法存在 Authentication 中，因此在此处手动设置下
            //User sysUser = SecurityUtils.getLoginUser();
            identityService.setAuthenticatedUserId(initiator);

            //设置流程中需要的变量
            variables.put(ProcessConstants.PROCESS_INITIATOR, "");

            ProcessInstance processInstance = runtimeService.startProcessInstanceById(procDefId, variables);
            // 给第一步申请人节点设置任务执行人和意见 , 该操作可以省略，就是在查看操作历史是，第一步无处理意见  默认是通过的
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();
            if (Objects.nonNull(task)) {
                taskService.addComment(task.getId(), processInstance.getProcessInstanceId(), FlowComment.NORMAL.getType(), initiator + "发起流程申请");
//                taskService.setAssignee(task.getId(), sysUser.getUserId().toString());
                taskService.complete(task.getId(), variables);
            }
            return AjaxResult.success("流程启动成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("流程启动错误");
        }

    }

    /**
     * 手动操作流程
     * @param state    状态
     * @param deployId 流程部署ID
     */
    @Override
    public void updateState(Integer state, String deployId) {
        ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
        // 激活
        if (state == 1) {
            repositoryService.activateProcessDefinitionById(procDef.getId(), true, null);
        }
        // 挂起
        if (state == 2) {
            repositoryService.suspendProcessDefinitionById(procDef.getId(), true, null);
        }
    }

    /**
     * 删除流程定义
     * @param deployId 流程部署ID act_ge_bytearray 表中 deployment_id值
     */
    @Override
    public void delete(String deployId) {
        repositoryService.deleteDeployment(deployId, true);
    }

    /**
     * 获取流程图
     *
     * @param deployId
     * @return
     */
    @Override
    public InputStream readImage(String deployId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
        //获得图片流
        DefaultProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
        //输出为图片
        return diagramGenerator.generateDiagram(
                bpmnModel,
                "png",
                Collections.emptyList(),
                Collections.emptyList(),
                "宋体",
                "宋体",
                "宋体",
                null,
                1.0,
                false);
    }
}
