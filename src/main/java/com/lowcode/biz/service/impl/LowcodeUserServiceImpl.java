package com.lowcode.biz.service.impl;

import com.lowcode.biz.entity.User;
import com.lowcode.biz.repository.LowcodeUserRepository;
import com.lowcode.biz.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @PACKAGE_NAME: com.fusiontech.lowcode.flowable.service.impl
 * @NAME: LowcodeUserServiceImpl
 * @USER: jack
 * @DATE: 11/21/2022
 * @PROJECT_NAME: flowable-master
 **/
@Service
@RequiredArgsConstructor
public class LowcodeUserServiceImpl implements IUserService {
    private final LowcodeUserRepository lowcodeUserRepository;
    @Override
    public User findById(String id) {
        return lowcodeUserRepository.findById(id).get();
    }

    @Override
    public User findByEmail(String email) {
        return lowcodeUserRepository.findByEmail(email);
    }
}
