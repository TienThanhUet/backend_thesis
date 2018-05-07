package com.stadio.sso.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.stadio.common.define.Constant;
import com.stadio.common.service.PasswordService;
import com.stadio.common.utils.HttpClient;
import com.stadio.common.utils.RoleType;
import com.stadio.common.utils.StringUtils;
import com.stadio.model.documents.User;
import com.stadio.model.repository.UserRepository;
import com.stadio.sso.custom.SSOUsernamePasswordAuthenticationToken;
import com.stadio.sso.service.IMessageService;
import com.stadio.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private IMessageService messageService;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userDetails = userRepository.findOneByUsername(username);

        if (userDetails == null)
        {
            throw new UsernameNotFoundException("Not found users");
        }

        return userDetails;
    }

    @Override
    public void createRootUser() {
        User user = userRepository.findOneByUsername("root");
        if (user == null) {
            user = new User();
            String rand = StringUtils.identifier256();
            user.setPasswordRand(rand);
            user.setPasswordHash(PasswordService.hidePassword("123456a@", rand));
            user.setUsername("root");
            user.setFullname("root");
            user.setEmail("support@uet.com");
            user.setEnabled(true);

            userRepository.save(user);
        }
    }

    @Override
    public Authentication processLoginByFacebook(SSOUsernamePasswordAuthenticationToken loginDTO) throws AuthenticationException
    {
        //request to Facebook SDK for check verify login
        Boolean isVaildFacebookToken;
        try {
            isVaildFacebookToken = verifyFBAccessToken3rd(loginDTO.getAccessToken(), loginDTO.getFacebookId());
        } catch (Exception e) {
            isVaildFacebookToken = false;
        }
        if (isVaildFacebookToken)
        {
            //Query User and response
            User user = userRepository.findOneByFacebookId(loginDTO.getFacebookId());
            if (user != null) {
                if (!user.isEnabled()) {
                    throw new BadCredentialsException("user.status.disabled");
                }
                loginDTO.setUserId(user.getId());
                return loginDTO;
            }

            //If not found user
            user = new User();
            user.setUsername(loginDTO.getFacebookId());
            user.setFullname(loginDTO.getFullname());
            user.setFacebookId(loginDTO.getFacebookId());
            user.setFacebookAccessToken(loginDTO.getAccessToken());
            user.setEmail(loginDTO.getEmail());

            userRepository.save(user);
            loginDTO.setUserId(user.getId());
            return loginDTO;
        } else {
            throw new BadCredentialsException(getMessage("user.invalid.facebook"));
        }
    }

    @Override
    public Authentication processLoginByGoogle(SSOUsernamePasswordAuthenticationToken loginDTO)
    {
        //request to Facebook SDK for check verify login
        Boolean isVaildGoogleToken;
        try {
            isVaildGoogleToken = verifyGGAccessToken3rd(loginDTO.getAccessToken(), loginDTO.getGoogleId());
        } catch (Exception e) {
            throw new BadCredentialsException(getMessage("user.invalid.google"));
        }
        if (isVaildGoogleToken)
        {
            //Query User and response
            User user = userRepository.findOneByGoogleId(loginDTO.getGoogleId());
            if (user != null) {
                if (!user.isEnabled()) {
                    throw new BadCredentialsException(getMessage("user.status.disabled"));
                }
                loginDTO.setUserId(user.getId());
                return loginDTO;
            }

            //If not found user
            user = new User();
            user.setUsername(loginDTO.getGoogleId());
            user.setFullname(loginDTO.getFullname());
            user.setGoogleId(loginDTO.getGoogleId());
            user.setGoogleAccessToken(loginDTO.getAccessToken());
            user.setEmail(loginDTO.getEmail());

            userRepository.save(user);
            loginDTO.setUserId(user.getId());
            return loginDTO;
        } else {
            throw new BadCredentialsException(getMessage("user.invalid.google"));
        }
    }

    private Boolean verifyFBAccessToken3rd(String accessToken, String facebookId) throws Exception {
        CompletableFuture<Boolean> combinedFuture = CompletableFuture.supplyAsync(
                () ->
                {
                    String url = Constant.VERIFY_FACEBOOK_TOKEN_URL +
                            "?access_token=" + Constant.FACEBOOK_APP_ID + "|" + Constant.FACEBOOK_APP_SECRET +
                            "&input_token=" + accessToken;
                    try {
                        JsonNode response = HttpClient.jsonNodeFromSyncRequest(url);
                        response = response.get("data");
                        String appId = response.get("app_id").textValue();
                        String userId = response.get("user_id").textValue();
                        return (appId.compareTo(Constant.FACEBOOK_APP_ID) == 0 &&
                                userId.compareTo(facebookId) == 0);
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return combinedFuture.get();
    }

    private Boolean verifyGGAccessToken3rd(String accessToken, String googleId) throws Exception {
        CompletableFuture<Boolean> combinedFuture = CompletableFuture.supplyAsync(
                () ->
                {
                    try {
                        JsonNode response = HttpClient.jsonNodeFromSyncRequest(Constant.VERIFY_GOOGLE_TOKEN_URL + accessToken);
                        String appId = response.get("azp").textValue();
                        String userId = response.get("sub").textValue();
                        return ((Constant.FIREBASE_GOOGLE_IOS_CLIENT_ID.compareTo(appId) == 0 || Constant.FIREBASE_GOOGLE_ANDROID_CLIENT_ID.compareTo(appId) == 0)
                                && userId.compareTo(googleId) == 0);
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return combinedFuture.get();
    }

    protected String getMessage(String key)
    {
        try {
            return messageService.getMessage(key);
        } catch (Exception e) {
            return key;
        }
    }
}
