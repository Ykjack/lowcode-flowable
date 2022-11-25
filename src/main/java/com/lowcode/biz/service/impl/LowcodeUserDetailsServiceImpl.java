package com.lowcode.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.lowcode.biz.repository.LowcodeUserRepository;
import com.lowcode.biz.resp.LowcodeDetailResp;
import com.lowcode.biz.entity.User;
import com.lowcode.biz.service.IUserService;
import com.lowcode.flowable.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PACKAGE_NAME: com.fusiontech.lowcode.flowable.zw.service.impl
 * @NAME: LowcodeUserDetailsServiceImpl
 * @USER: jack
 * @DATE: 11/21/2022
 * @PROJECT_NAME: flowable-master
 **/
@Service
@RequiredArgsConstructor
public class LowcodeUserDetailsServiceImpl implements UserDetailsService {

    private final IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User currentUser = null;
//        User user = new User();
//        user.setName(username);
        User user = userService.findByEmail(username);
        //user.setIsEnabled(true);
        //User a = userRepository.findById("636902b9e5c4e22275a37b13").get();
        //saveUser();
//        List<User> all = userRepository.findAll();
//        List<User> users = userRepository.findAll(Example.of(user));
//        if(CollUtil.isNotEmpty(users)){
//            lowcodeDetailResp = BeanUtil.toBean(users.get(0), LowcodeDetailResp.class);
//        }
        if(user == null){
            throw  new CustomException("user 未发现");
        }
         return user;
    }

//    public void saveUser(){
//        User user = new User();
//        user.setName("admin");
//        user.setPassword("123456");
//        user.setEmail("1111");
//        user.setIsEnabled(Boolean.TRUE);
//        userRepository.save(user);
//    }
}
