package com.stadio.restapi.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stadio.model.documents.SSOAccessToken;
import com.stadio.model.documents.User;
import com.stadio.model.repository.UserRepository;
import com.stadio.model.repository.custom.SSOAccessTokenRepository;
import com.stadio.restapi.i18n.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class BaseService
{
    @Autowired
    private IMessageService messageService;

    @Autowired SSOAccessTokenRepository tokenRepository;

    @Autowired
    UserRepository managerRepository;

    protected ObjectMapper mapper = new ObjectMapper();

    @Autowired UserRepository userRepository;

    protected String getMessage(String key)
    {
        try {
            return messageService.getMessage(key);
        } catch (Exception e) {
            return "OK";
        }
    }

    public User getUserByAccessToken(String accessToken)
    {
        accessToken = accessToken.replace("Bearer ", "");
        String tokenKey = extractTokenKey(accessToken);
        SSOAccessToken ssoAccessToken = tokenRepository.findByTokenId(tokenKey);
        return userRepository.findOne(ssoAccessToken.getUserId());
    }

    protected String extractTokenKey(String value) {
        if (value == null) {
            return null;
        }
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
        }

        try {
            byte[] bytes = digest.digest(value.getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
        }
    }
}
