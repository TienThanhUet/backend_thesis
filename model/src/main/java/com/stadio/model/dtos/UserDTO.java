package com.stadio.model.dtos;

import lombok.Data;

/**
 * Created by Andy on 11/08/2017.
 */
@Data
public class UserDTO
{
    private String id;
    private String fullname;
    private String username;
    private String password;
    private String email;
}
