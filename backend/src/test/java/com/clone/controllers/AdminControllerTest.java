package com.clone.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pontusellboj on 2017-04-17.
 */
public class AdminControllerTest {

    private AdminController adminController = new AdminController();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test1() throws Exception {
        assertEquals("Only admins can se me...", adminController.test());
    }

    @Test
    public void test2() throws Exception {
        assertFalse(adminController.test() != "Only admins can se me...");
    }

}