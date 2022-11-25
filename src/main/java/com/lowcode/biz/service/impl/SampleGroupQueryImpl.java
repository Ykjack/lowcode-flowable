package com.lowcode.biz.service.impl;

import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.idm.api.Group;
import org.flowable.idm.engine.impl.GroupQueryImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: mayike
 * @Date: 2022/11/21 14:58
 */
public class SampleGroupQueryImpl extends GroupQueryImpl {
    @Override
    public List<Group> executeList(CommandContext commandContext) {
        return new ArrayList<>();
    }
}
