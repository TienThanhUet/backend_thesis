package com.stadio.sso.service;

import com.stadio.sso.custom.SSOUsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    public Authentication processLoginByFacebook(SSOUsernamePasswordAuthenticationToken loginDTO) throws AuthenticationException;

    public Authentication processLoginByGoogle(SSOUsernamePasswordAuthenticationToken loginDTO);

    public void createRootUser();

}
