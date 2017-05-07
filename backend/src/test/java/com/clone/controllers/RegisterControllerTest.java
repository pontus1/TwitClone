package com.clone.controllers;

import com.clone.TwitterCloneApplication;
import com.clone.entities.User;
import com.clone.repositories.UserRepository;
import com.clone.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManagerFactory;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by pontusellboj on 2017-04-17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TwitterCloneApplication.class)
@WebAppConfiguration
public class RegisterControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Mock
    private User user;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void registerUserWithoutParams() throws Exception {

        // Bad request = 400
        mockMvc.perform(post("/register/"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void registerUserWithParams() throws Exception {

        this.user = new User();

        user.setUsername("Jane Doe");
        user.setEmail("janedoe@test.com");
        user.setPassword("password");

        // Is created = 201
        this.mockMvc.perform(post("/register/")
                .contentType(contentType)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().string("{\"userId\":0,\"username\":\"Jane Doe\",\"email\":\"janedoe@test.com\",\"password\":\"password\",\"enabled\":1,\"followers\":null}")); // TODO: Se why userId = 0
    }

}