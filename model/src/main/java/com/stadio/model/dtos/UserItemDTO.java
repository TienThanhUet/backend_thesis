package com.stadio.model.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stadio.model.documents.User;
import lombok.Data;

import java.util.Date;

@Data
public class UserItemDTO
{
    private String id;
    private String username;
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd/MM/yyyy")
    private Date createdDate;

    public UserItemDTO(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.createdDate = user.getCreatedDate();

    }
}
