package com.shuhaliia.phonebook.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.shuhaliia.phonebook.config.PhonebookApplicationInitializer;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=PhonebookApplicationInitializer.class)
public class HomeControllerTest {
	
	HomeController controller;
	
	public HomeControllerTest(){
		controller = new HomeController();
	}

	@Test
	public void showPhoneBookPageTest(){
		
		MockMvc mock = standaloneSetup(controller).build();
		
		try {
			mock.perform(get("/"))
				.andExpect(view().name("phonebookPage"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@Test
	public void showLoginPageTest(){
		
		MockMvc mock = standaloneSetup(controller).build();
		try {
			mock.perform(get("/login"))
				.andExpect(view().name("login"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void showRegisterPageTest(){
		
		MockMvc mock = standaloneSetup(controller).build();
		
		try {
			mock.perform(get("/register"))
				.andExpect(view().name("register"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
