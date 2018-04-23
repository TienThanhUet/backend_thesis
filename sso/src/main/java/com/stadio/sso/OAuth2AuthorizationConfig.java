package com.stadio.sso;


import com.stadio.sso.custom.SSOTokenGranter;
import com.stadio.sso.custom.SSOWebResponseExceptionTranslator;
import com.stadio.sso.custom.SSOTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import java.util.concurrent.TimeUnit;

@ComponentScan("com.stadio")
@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties({AuthorizationServerProperties.class})
public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter
{
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthorizationServerProperties authorizationServerProperties;

    @Autowired
    SSOTokenStore ssoTokenStore;

    @Autowired
    private SSOWebResponseExceptionTranslator ssoWebResponseExceptionTranslator;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception
    {
        clients.inMemory()
                .withClient("restapi")
                .secret("restapi")
                .authorizedGrantTypes("password")
                .scopes("read", "write")
                .accessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(10))
                .autoApprove(true)
            .and()
                .withClient("web-service")
                .secret("restapi")
                .authorizedGrantTypes("password")
                .scopes("read", "write")
                .accessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(10))
                .autoApprove(true)
            .and()
                .withClient("data-integration")
                .secret("restapi")
                .authorizedGrantTypes("password")
                .scopes("read", "write")
                .accessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(10))
                .autoApprove(true)
            .and()
                .withClient("mobileapi")
                .secret("restapi")
                .authorizedGrantTypes("password")
                .scopes("read", "write")
                .accessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(10))
                .autoApprove(true)
            .and()
                .withClient("mediationApi")
                .secret("restapi")
                .authorizedGrantTypes("password")
                .scopes("read", "write")
                .accessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(10))
                .autoApprove(true)
        ;
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception
    {
        endpoints.authenticationManager(authenticationManager);
        endpoints.tokenStore(ssoTokenStore);

        SSOTokenGranter ssoTokenGranter = new SSOTokenGranter(authenticationManager,endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory());
        endpoints.tokenGranter(ssoTokenGranter);
        endpoints.exceptionTranslator(ssoWebResponseExceptionTranslator);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception
    {
        security.tokenKeyAccess(authorizationServerProperties.getTokenKeyAccess());
        security.checkTokenAccess("permitAll()");
    }

}