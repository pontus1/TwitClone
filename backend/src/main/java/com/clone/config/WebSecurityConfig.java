package com.clone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

import javax.sql.DataSource;

/**
 * Created by pontusellboj on 2017-01-28.
 */
@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
public class WebSecurityConfig extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    /**
     * Configure security and rights for users in database and in-memory user
     *
     * principal = users username
     * credentials = users password
     *
     * authorities are based on user-role and enabled (false if user is blocked)
     *
     * An in-memory user is created with user-role = ANONYMOUS to allow access to registration
     * for unregistered users.
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("register").password("register").roles("ANONYMOUS");

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT username AS principal, password AS credentials, enabled FROM user WHERE username=?")
                .authoritiesByUsernameQuery("SELECT user.username, user_role.role FROM user_role JOIN user ON user.user_id = user_role.user_id WHERE user.username=?");
        }
}