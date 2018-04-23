package com.stadio.sso.config;

import com.stadio.sso.AuthorizationApplication;
import com.stadio.sso.config.SimpleCorsFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

/**
 * Created by Andy on 11/25/2017.
 */
@Configuration
@EnableWebSecurity
public class LoginConfig extends WebSecurityConfigurerAdapter
{

    private Logger logger = LogManager.getLogger(AuthorizationApplication.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        logger.info("**** SSO Config authentication ****");
        http.addFilterBefore(new SimpleCorsFilter(), ChannelProcessingFilter.class)
                .formLogin().loginPage("/login").permitAll()
                .and()
                .requestMatchers()
                .antMatchers("/", "/login", "/oauth/authorize", "/oauth/confirm_access", "oauth/token")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();
    }

}
