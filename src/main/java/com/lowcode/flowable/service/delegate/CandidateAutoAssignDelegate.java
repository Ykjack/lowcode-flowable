package com.lowcode.flowable.service.delegate;

import com.lowcode.flowable.common.component.SpringUtil;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;

import java.util.List;

/**
 * 审批候选人分配
 * @Author: mayike
 * @Date: 2022/11/21 14:58
 */
public abstract class CandidateAutoAssignDelegate extends AbstractAssignDelegate {

    @Override
    protected void doAutoAssign(Task task, UserDepartment userDepartment, String userId, String department, String role, List<String> userGroupList) {
        String taskId = task.getId();
        TaskService taskService = SpringUtil.getBean(TaskService.class);
        if (null != userId) {
            taskService.addCandidateUser(taskId, userId);
        } else if (null != department) {
            taskService.addCandidateGroup(taskId, department);
        } else if (null != role) {
            taskService.addCandidateGroup(taskId, role);
        } else if (null != userGroupList && userGroupList.size() > 0) {
            String candidateType = userGroupList.get(0);
            switch (candidateType) {
                case CandidateType.USER:
                    for (int i = 1; i < userGroupList.size(); i++) {
                        taskService.addCandidateUser(taskId, userGroupList.get(i));
                    }
                    break;
                case CandidateType.DEPARTMENT:
                    for (int i = 1; i < userGroupList.size(); i++) {
                        taskService.addCandidateGroup(taskId, userGroupList.get(i));
                    }
                    break;
                case CandidateType.DEPARTMENT_ROLE:
                    String _department = userGroupList.get(1);
                    for (int i = 2; i < userGroupList.size(); i++) {
                        taskService.addCandidateGroup(taskId, _department + ":" + userGroupList.get(i));
                    }
                    break;
                default:
                    throw new IllegalArgumentException("候选类型不合法");
            }
        } else {
            // 如果没有找到合适的候选人就由业务决定如何处理
            //taskService.addCandidateUser(taskId, userDepartment.userId);
            assignIfNoCandidate(task, userDepartment);
        }
    }

    /**
     * 如果没有合适的候选人如何处理
     * @param taskId
     * @param userDepartment
     */
    protected abstract void assignIfNoCandidate(Task task, UserDepartment userDepartment);
}
