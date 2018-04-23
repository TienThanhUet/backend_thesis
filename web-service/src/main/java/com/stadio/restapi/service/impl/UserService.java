package com.stadio.restapi.service.impl;


import com.stadio.common.service.PasswordService;
import com.stadio.common.utils.ResponseCode;
import com.stadio.common.utils.RoleType;
import com.stadio.common.utils.StringUtils;
import com.stadio.model.documents.User;
import com.stadio.model.documents.SSOAccessToken;
import com.stadio.model.dtos.UserDTO;
import com.stadio.model.dtos.UserDetailDTO;
import com.stadio.model.repository.UserRepository;
import com.stadio.restapi.response.ResponseResult;
import com.stadio.restapi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class UserService extends BaseService implements IUserService
{

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseResult processCreateNewUser( UserDTO userDTO)
    {

        ResponseResult responseResult = validUserDTO(userDTO);

        if (responseResult != null)
        {
            return responseResult;
        }

        User newUser = new User();
        
        newUser.setEmail(userDTO.getEmail());
        newUser.setUsername(userDTO.getUsername());

        String noise = new Date().toString() + UUID.randomUUID().toString();
        String hidePass = PasswordService.hidePassword(userDTO.getPassword(), noise);

        newUser.setPasswordHash(hidePass);
        newUser.setPasswordRand(noise);

        userRepository.save(newUser);

        return ResponseResult.newInstance(ResponseCode.SUCCESS, getMessage("user.success.register"), null);

    }

    @Override
    public User getCurrentUser(String accessToken)
    {
        accessToken = accessToken.replace("Bearer ", "");
        String tokenKey = extractTokenKey(accessToken);
        SSOAccessToken ssoAccessToken = tokenRepository.findByTokenId(tokenKey);
        return userRepository.findOne(ssoAccessToken.getUserId());
    }

    private ResponseResult validUserDTO(UserDTO userDTO)
    {
        if (!StringUtils.isNotNull(userDTO.getEmail()) || !StringUtils.isValidEmailAddress(userDTO.getEmail()))
        {
            return ResponseResult.newInstance(ResponseCode.MISSING_PARAM, getMessage("user.invalid.email"), null);
        }

        if (!StringUtils.isNotNull(userDTO.getPassword()) || !StringUtils.isValidPassword(userDTO.getPassword()))
        {
            return ResponseResult.newInstance(ResponseCode.MISSING_PARAM, getMessage("user.invalid.passWord"), null);
        }
        
        if (!StringUtils.isNotNull(userDTO.getUsername()))
        {
            return ResponseResult.newInstance(ResponseCode.MISSING_PARAM, getMessage("user.invalid.userName"), null);
        }

        List<User> users = userRepository.findUserByEmail(userDTO.getEmail());
        if (users != null && !users.isEmpty())
        {
            return ResponseResult.newInstance(ResponseCode.EXIST_VALUE, getMessage("user.exist.email"), null);
        }

        users = userRepository.findUserByUsername(userDTO.getUsername());
        if (users != null && !users.isEmpty())
        {
            return ResponseResult.newInstance(ResponseCode.EXIST_VALUE, getMessage("user.exist.useName"), null);
        }
        return null;
    }

    @Override
    public ResponseResult<?> processUpdateUser(UserDTO userDTO)
    {

        User user = findOneByUsername(userDTO.getUsername());

        if (user == null)
        {
            return ResponseResult.newInstance(ResponseCode.MISSING_PARAM, getMessage("user.notFound.id"), null);
        }

        user.setUpdatedDate(new Date());

        if (StringUtils.isNotNull(userDTO.getEmail()))
        {
            if (StringUtils.isValidEmailAddress(userDTO.getEmail()))
            {
                user.setEmail(userDTO.getEmail());
            }
            else
            {
                return ResponseResult.newInstance(ResponseCode.MISSING_PARAM, getMessage("user.invalid.email"), null);
            }
        }

        userRepository.save(user);

        return ResponseResult.newInstance(ResponseCode.SUCCESS, getMessage("user.success.update"), new UserDetailDTO(user));
    }
    

    @Override
    public ResponseResult processChangePassword(String token, String oldPass, String newPass)
    {

        User user = getCurrentUser(token);

        if (PasswordService.validPassword(oldPass, user.getPasswordRand(), user.getPasswordHash()))
        {
            if (!StringUtils.isNotNull(newPass) || !StringUtils.isValidPassword(newPass))
            {
                return ResponseResult.newInstance(ResponseCode.FAIL, getMessage("user.invalid.passWord"), null);
            }
            else
            {
                user.setUpdatedDate(new Date());

                String noise = StringUtils.identifier256();
                String hidePass = PasswordService.hidePassword(newPass, noise);

                user.setPasswordHash(hidePass);
                user.setPasswordRand(noise);

                userRepository.save(user);

                return ResponseResult.newInstance(ResponseCode.SUCCESS, getMessage("user.success.changePassword"), null);
            }
        }
        else
        {
            return ResponseResult.newInstance(ResponseCode.FAIL, getMessage("user.notMath.passWord"), null);
        }
    }
    

    @Override
    public ResponseResult<?> processGetProfileUser(String id)
    {
        if (!StringUtils.isNotNull(id))
        {
            return ResponseResult.newInstance(ResponseCode.MISSING_PARAM, getMessage("user.invalid.id"), null);
        }

        User user = userRepository.findOne(id);

        if (user == null)
        {
            return ResponseResult.newInstance(ResponseCode.MISSING_PARAM, getMessage("user.notFound.id"), null);
        }
        else
        {
            return ResponseResult.newInstance(ResponseCode.SUCCESS, getMessage("user.success.getProfile"), new UserDetailDTO(user));
        }
    }

    public User findOneByUsername(String currentName)
    {
        return userRepository.findOneByUsername(currentName);
    }
}
