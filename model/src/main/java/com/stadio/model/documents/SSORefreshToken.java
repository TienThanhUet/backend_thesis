package com.stadio.model.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection="sso_refresh_token")
public class SSORefreshToken implements Serializable {

    @Id
    private String tokenId;
    private byte[] token;
    private byte[] authentication;
    private Boolean isExpired;

    public SSORefreshToken() {
        this.isExpired = false;
    }

//    @PersistenceConstructor
    public SSORefreshToken(final String tokenId,
                           final byte[] token,
                           final byte[] authentication) {
        this.tokenId = tokenId;
        this.token = token;
        this.authentication = authentication;
        this.isExpired = false;
    }

    public String getTokenId() {
        return tokenId;
    }

    public byte[] getToken() {
        return token;
    }

    public byte[] getAuthentication() {
        return authentication;
    }
}
