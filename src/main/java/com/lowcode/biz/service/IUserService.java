package com.lowcode.biz.service;

import com.lowcode.biz.entity.User;

public interface IUserService {
    public User findById(String id);

    User findByEmail(String email);
}
