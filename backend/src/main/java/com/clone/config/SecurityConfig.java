package com.clone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.sql.DataSource;

/**
 * Created by pontusellboj on 2017-01-25.
 */
@Configuration
@EnableWebSecurity
//@Order(3)
public class SecurityConfig {} /* extends WebSecurityConfigurerAdapter {



    @Override
    public void configure(HttpSecurity http) throws Exception {


        http    //.csrf().disable()
                //.anonymous().and()
                .requestMatcher(
                        new OrRequestMatcher(
                                new AntPathRequestMatcher("/api/**"),
                                new AntPathRequestMatcher("/greeting2")
                        )
                )

                .authorizeRequests()
                .antMatchers("/oauth/token").permitAll()
                //.antMatchers("/**").permitAll()
                //.antMatchers("/api/users/**", "/api/tweets/admin").access("hasRole('ADMIN')")
                //.antMatchers("/api/users/admin/**").hasRole("ADMIN")
                .antMatchers("/api/**").access("#oauth2.hasScope('trust')")
                //.anyRequest().authenticated()
                .and()
                .httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    System.out.println("IN HTTP SECURITY...");
/*
        http  //.csrf().disable()
                .antMatcher("/api/**")
                .authorizeRequests()
                //.antMatchers("/api/users/**").hasRole("ADMIN")
                //.antMatchers(HttpMethod.POST, "/api/**").authenticated()
                .anyRequest().hasRole("USER")
                .and()
                .logout().logoutSuccessUrl("/")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.ACCEPTED)).permitAll()
                //.logoutRequestMatcher(new AntPathRequestMatcher("/login/logout")).logoutSuccessUrl("/login/")
                .and().httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //.and().sessionManagement().maximumSessions(2);

*//*
    }

}
*/
// The order of the rules matters and the more specific rules should go first
