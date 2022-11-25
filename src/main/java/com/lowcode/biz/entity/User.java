package com.lowcode.biz.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.*;


@Getter
@Setter
@ToString
@Document
public class User implements UserDetails {

    private static final long serialVersionUID = 7459916000501322517L;

    @Id
    private String id;

    private String name;

    private String openid;

    private String email;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonIgnore
    private Boolean passwordResetInitiated = false;

    private String source = "FORM";

    private String state;

    private Boolean isEnabled = true;

    private String currentOrganizationId;

    private Set<String> organizationIds;

    private String examplesOrganizationId;

    // There is a many-to-many relationship with groups. If this value is modified, please also modify the list of
    // users in that particular group document as well.
    private Set<String> groupIds = new HashSet<>();

    // These permissions are in addition to the privileges provided by the groupIds. We can assign individual permissions
    // to users instead of creating a group for them. To be used only for one-off permissions.
    // During evaluation a union of the group permissions and user-specific permissions will take effect.
    private Set<String> permissions = new HashSet<>();


    //    @JsonIgnore
    @Indexed
    @CreatedDate
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(locale = "zh", timezone = "Asia/Shanghai", pattern = "yyyy-MM-dd HH:mm:ss")
    protected Instant createdAt;

    //    @JsonIgnore
    @LastModifiedDate
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(locale = "zh", timezone = "Asia/Shanghai", pattern = "yyyy-MM-dd HH:mm:ss")
    protected Instant updatedAt;

    @CreatedBy
    protected String createdBy;

    @LastModifiedBy
    protected String modifiedBy;

    protected Boolean deleted = false;

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isEnabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isEnabled;
    }

    @Override
    public boolean isEnabled() {
        // The `isEnabled` field is `Boolean` whereas we are returning `boolean` here. If `isEnabled` field value is
        // `null`, this would throw a `NullPointerException`. Hence, checking equality with `Boolean.TRUE` instead.
        return Boolean.TRUE.equals(this.isEnabled);
    }


    @Transient
    @JsonIgnore
    public String computeFirstName() {
        return (StringUtils.isEmpty(name) ? email : name).split("[\\s@]+", 2)[0];
    }
}
