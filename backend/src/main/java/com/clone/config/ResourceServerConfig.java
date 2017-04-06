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


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(OAuth2Config.RESOURCE_ID);
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers().antMatchers("/api/**")
                .and()
                .authorizeRequests()
                .antMatchers("/register/").hasRole("ANONYMOUS")
                //.antMatchers("/api/users/admin/**").hasRole("ADMIN")
                .antMatchers("/api/users", "/api/tweets").hasRole("USER");
                //.anyRequest().access("#oauth2.hasScope('scope1')");

    }
}
