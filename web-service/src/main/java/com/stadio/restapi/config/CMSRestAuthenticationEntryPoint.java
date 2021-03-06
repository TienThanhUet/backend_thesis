package com.stadio.restapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stadio.restapi.i18n.IMessageService;
import com.stadio.restapi.response.ResponseResult;
import com.stadio.common.custom.RestAuthenticationEntryPoint;
import com.stadio.common.utils.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CMSRestAuthenticationEntryPoint extends RestAuthenticationEntryPoint {

    @Autowired
    private IMessageService messageService;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, org.springframework.security.core.AuthenticationException e) throws IOException, ServletException {
        String message = "login.token.invaild";
        try {
            message = messageService.getMessage(message);
        } catch (Exception exp) {
        }
        ResponseResult responseResult = ResponseResult.newErrorInstance(ResponseCode.AUTHENTICATE_FAIL,message);
        ObjectMapper mapper = new ObjectMapper();
        super.commence(httpServletRequest,httpServletResponse,e,mapper.writeValueAsString(responseResult));
    }
}