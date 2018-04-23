package com.stadio.sso;

import com.stadio.sso.config.LoginConfig;
import com.stadio.sso.service.impl.UserServiceImpl;
import com.stadio.sso.custom.SSOAuthenticationKeyGenerator;
import org.apache.catalina.filters.RequestDumperFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.*;

import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;

@SpringBootApplication(scanBasePackages = {"com.stadio"})
@ComponentScan(basePackages = {"com.stadio.sso"})
@Import({LoginConfig.class})
public class AuthorizationApplication extends SpringBootServletInitializer
{

    private static Logger logger = LogManager.getLogger(AuthorizationApplication.class);

    @Autowired
    private UserServiceImpl userDetailsService;

    @Bean
    public AuthenticationKeyGenerator authenticationKeyGenerator() {
        return new SSOAuthenticationKeyGenerator();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(AuthorizationApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationApplication.class, args);
    }

    @Profile("!cloud")
    @Bean
    RequestDumperFilter requestDumperFilter()
    {
        return new RequestDumperFilter();
    }

    @Bean
    public ApplicationRunner initialize()
    {
        return applicationArguments ->
        {
            logger.info("ApplicationRunner initialize");
            userDetailsService.createRootUser();
        };
    }
}
