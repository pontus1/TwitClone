package com.clone.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * Created by pontusellboj on 2017-01-28.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    /**
     * Configure allowed client applications
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(OAuth2Config.RESOURCE_ID);
    }


    /**
     * Configure security and rights for endpoints
     *
     * Rights are based on user-roles. An in-memory user with role = ANONYMOUS
     * is created for the register endpoint, which allows access for unregistered users.
     * Users with role = USER only has access to users- and tweets endpoints.
     * Users with role = ADMIN has access to all endpoints.
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers().antMatchers("/api/**")
                .and()
                .authorizeRequests()
                .antMatchers("/register/").hasRole("ANONYMOUS")
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/users/**", "/api/tweets/**").access("hasRole('ADMIN') or hasRole('USER')");
    }
}
