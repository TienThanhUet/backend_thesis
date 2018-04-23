package com.stadio.model.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stadio.common.service.PasswordService;
import com.stadio.common.utils.StringUtils;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
public class UserBase  implements UserDetails {

    static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field(value = "user_name")
    private String username;

    @Field(value = "password")
    private String password;

    @Field(value = "enabled")
    private boolean enabled;

    @Field(value = "email")
    private String email;

    @Field(value = "email_confirmed")
    private Boolean emailComfirmed;

    @JsonIgnore
    @Field(value = "password_hash")
    private String passwordHash;

    @JsonIgnore
    @Field(value = "password_rand")
    private String passwordRand;

    @Field(value = "facebook_id")
    private String facebookId;

    @Field(value = "google_id")
    private String googleId;

    @Field(value = "facebook_access_token")
    private String facebookAccessToken;

    @Field(value = "google_access_token")
    private String googleAccessToken;

    @Field(value = "created_date")
    private Date createdDate;

    @Field(value = "updated_date")
    private Date updatedDate;


    public UserBase() {
        this.enabled = true;
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // we never lock accounts
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // credentials never expire
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setNewPass(String pass) {
        String rand = StringUtils.identifier256();
        this.setPasswordRand(rand);
        this.setPasswordHash(PasswordService.hidePassword(pass, rand));
    }

}
