package com.lowcode.biz.resp;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @PACKAGE_NAME: com.fusiontech.lowcode.flowable.model.resp
 * @NAME: LowcodeDetailResp
 * @USER: jack
 * @DATE: 11/21/2022
 * @PROJECT_NAME: flowable-master
 **/
@Data
public class LowcodeDetailResp implements UserDetails {

    String id;
    String name;
    String email;
    boolean isEnabled;

    String passWord;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return passWord;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
