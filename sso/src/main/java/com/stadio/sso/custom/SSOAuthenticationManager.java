package com.stadio.sso.custom;

import com.stadio.common.service.PasswordService;
import com.stadio.common.utils.RoleType;
import com.stadio.model.documents.User;
import com.stadio.model.model.UserBase;
import com.stadio.sso.service.IMessageService;
import com.stadio.sso.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class SSOAuthenticationManager implements AuthenticationManager
{

    private Logger logger = LogManager.getLogger(SSOAuthenticationManager.class);

    @Autowired
    private IMessageService messageService;

    @Autowired
    private UserService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        SSOUsernamePasswordAuthenticationToken auth = (SSOUsernamePasswordAuthenticationToken)authentication;
        return handleUserLogin(auth);
    }

    private Authentication handleUserLogin(SSOUsernamePasswordAuthenticationToken auth)  throws AuthenticationException {
        if (auth.getLoginFacebook() != null && auth.getLoginFacebook()) {
            return userDetailsService.processLoginByFacebook(auth);
        } else if (auth.getLoginGoogle() != null && auth.getLoginGoogle()) {
            return userDetailsService.processLoginByGoogle(auth);
        } else {
            User user = (User) userDetailsService.loadUserByUsername(auth.getUsername());
            logger.info(">>> Authenticate with: " + auth.getUsername());
            return handlerPasswordAuthentication(user, auth);
        }
    }

    private Authentication handlerPasswordAuthentication(UserBase user, SSOUsernamePasswordAuthenticationToken auth) throws AuthenticationException
    {
        if (!user.isEnabled()) {
            throw new BadCredentialsException(messageService.getMessage("user.status.disabled"));
        }

        if (user != null && PasswordService.validPassword(auth.getPassword(), user.getPasswordRand(), user.getPasswordHash())) {
            auth.setUserId(user.getId());
            return auth;
        } else {
            throw new BadCredentialsException(messageService.getMessage("user.invalid.password"));
        }
    }
}
