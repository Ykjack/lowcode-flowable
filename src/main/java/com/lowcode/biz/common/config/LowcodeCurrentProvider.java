package com.lowcode.biz.common.config;

import com.lowcode.biz.entity.User;
import com.lowcode.biz.resp.LowcodeDetailResp;
import org.flowable.ui.common.model.UserRepresentation;
import org.flowable.ui.common.rest.idm.CurrentUserProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @PACKAGE_NAME: com.fusiontech.lowcode.flowable.common.config
 * @NAME: LowcodeCurrentProvider
 * @USER: jack
 * @DATE: 11/21/2022
 * @PROJECT_NAME: flowable-master
 **/
@Component
public class LowcodeCurrentProvider implements CurrentUserProvider {
    @Override
    public UserRepresentation getCurrentUser(Authentication authentication) {
        if (null == authentication || null == authentication.getPrincipal()) {
            return new UserRepresentation();
        }
        User currentUser = (User) authentication.getPrincipal();
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId(currentUser.getEmail());
        userRepresentation.setFullName(currentUser.getName());
        userRepresentation.setEmail(currentUser.getEmail());
        return userRepresentation;
    }

    @Override
    public boolean supports(Authentication authentication) {
        if (null == authentication) {
            return false;
        }
        return authentication.getPrincipal() instanceof User;
    }

    public User getSysUser(){
        User sysUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return sysUser;
    }
}
