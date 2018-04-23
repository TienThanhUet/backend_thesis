package com.stadio.model.dtos;

import com.stadio.model.documents.User;

import java.util.Date;

public class UserDetailDTO extends UserItemDTO
{

    private Date updatedDate;

    public UserDetailDTO(User user)
    {
        super(user);


        this.updatedDate = user.getUpdatedDate();
    }
}
