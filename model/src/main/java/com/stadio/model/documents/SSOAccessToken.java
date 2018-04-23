package com.stadio.model.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Data
@Document(collection="sso_access_token")
public class SSOAccessToken implements Serializable {

    @Id
    private String tokenId;

    private byte[] tokenSerialized;

    private String token;

    @Field("authentication_id")
    private String authenticationId;

    @TextIndexed
    @Field("user_id")
    private String userId;

    @Field("client_id")
    private String clientId;

    private byte[] authentication;

    @Field("refresh_token")
    private String refreshToken;

    private String refreshTokenId;

    @Field("is_expired")
    private Boolean isExpired;

    public SSOAccessToken() {
        this.isExpired = false;
    }

//    @PersistenceConstructor
    public SSOAccessToken(final String tokenId,
                          final byte[] tokenSerialized,
                          final String token,
                          final String authenticationId,
                          final String userId,
                          final String clientId,
                          final byte[] authentication,
                          final String refreshToken) {
        this.tokenId = tokenId;
        this.tokenSerialized = tokenSerialized;
        this.token = token;
        this.authenticationId = authenticationId;
        this.userId = userId;
        this.clientId = clientId;
        this.authentication = authentication;
        this.refreshToken = refreshToken;
        this.isExpired = false;
    }

	@Override
    public int hashCode() {
        return Objects.hash(tokenSerialized, authenticationId, userId, clientId, authentication, refreshToken);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SSOAccessToken other = (SSOAccessToken) obj;
        return Objects.equals(this.tokenSerialized, other.tokenSerialized) && Objects.equals(this.authenticationId, other.authenticationId) && Objects.equals(this.userId, other.userId) && Objects.equals(this.clientId, other.clientId) && Objects.equals(this.authentication, other.authentication) && Objects.equals(this.refreshToken, other.refreshToken);
    }

    @Override
    public String toString() {
        return "SSOAccessToken{" +
                "tokenId='" + tokenId + '\'' +
                ", token=" + Arrays.toString(tokenSerialized) +
                ", authenticationId='" + authenticationId + '\'' +
                ", userId='" + userId + '\'' +
                ", clientId='" + clientId + '\'' +
                ", authentication=" + Arrays.toString(authentication) +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
