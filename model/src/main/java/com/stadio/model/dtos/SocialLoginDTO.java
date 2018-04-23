package com.stadio.model.dtos;

import lombok.Data;


@Data
public class SocialLoginDTO
{
    private String email;
    private String phone;
    private String facebookId;
    private String googleId;
    private String accessToken;
    private String avatar;
    private String birthDay;
    private String fullName;

}
