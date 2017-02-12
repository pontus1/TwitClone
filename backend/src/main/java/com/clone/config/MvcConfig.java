package com.clone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by pontusellboj on 2017-01-25.
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/twitter_clone_db?useSSL=false");
        driverManagerDataSource.setUsername("user");
        driverManagerDataSource.setPassword("password");
        return driverManagerDataSource;
    }
}
