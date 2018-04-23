package com.stadio.sso.controller;

import com.stadio.sso.custom.SSOTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FrameworkEndpoint
public class UserController {

    @Autowired
    SSOTokenStore ssoTokenStore;

    @GetMapping("/oauth/token/revoke")
    @ResponseBody
    public void revokeToken(HttpServletRequest request,HttpServletResponse response) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.contains("Bearer")) {
            String token = authorization.substring("Bearer".length() + 1);
            ssoTokenStore.removeAccessToken(token);
            return;
        }
        try {
            response.sendError(400,"Token missing");

        } catch (Exception e) {
            response.setStatus(500);
        }
    }
}
