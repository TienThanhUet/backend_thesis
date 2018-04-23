package com.stadio.common.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class RequestHandler {

    @Autowired
    HttpServletRequest httpServletRequest;

    public RequestHandler() {

    }

    public String getToken() {

        return httpServletRequest.getHeader("Authorization");
    }

}
