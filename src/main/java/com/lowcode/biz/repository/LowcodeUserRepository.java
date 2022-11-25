package com.lowcode.biz.repository;

import com.lowcode.biz.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LowcodeUserRepository extends MongoRepository<User,String> {
        User findByEmail(String email);
}
