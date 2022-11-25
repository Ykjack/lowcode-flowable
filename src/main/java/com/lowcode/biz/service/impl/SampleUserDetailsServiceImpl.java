package com.lowcode.biz.service.impl;

import com.lowcode.biz.resp.SampleDetailResp;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Author: mayike
 * @Date: 2022/11/21 14:58
 */

//@Service
public class SampleUserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 根据自己的规则获取用户 TODO
        SampleDetailResp sampleDetailResp = new SampleDetailResp();
        sampleDetailResp.setAccount("hello");
        sampleDetailResp.setId(1);
        sampleDetailResp.setNickname("world");
        sampleDetailResp.setStatus(1);
        return sampleDetailResp;
    }
}
