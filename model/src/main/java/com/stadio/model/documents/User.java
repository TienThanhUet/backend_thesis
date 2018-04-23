package com.stadio.model.documents;

import com.stadio.model.model.UserBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "tab_user")
public class User extends UserBase implements Serializable
{

    public User() {
        super();
        this.setEnabled(true);
    }

}
