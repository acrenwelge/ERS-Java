package com.andrew.ers.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.andrew.ers.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class UserControllerTest {
	
	@Autowired
	MockMvc mvc;
	
	@Autowired
	ObjectMapper mapper;
	
	UserDTO testUser;
	
	@Before
	public void setup() {
		testUser = new UserDTO();
		testUser.setFirstName("Andrew");
		testUser.setLastName("Crenwelge");
		testUser.setPassword("password");
		testUser.setConfirmPassword("password");
		testUser.setAddress("123abc");
		testUser.setEmail("abc@gmail.com");
	}
	
	@Test
	public void testGetUsers() throws Exception {
		this.mvc.perform(get("/users"))
		  .andExpect(status().isOk())
		  .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
	}
	
	@Test
	public void testCreateUsers() throws Exception {
		String json = mapper.writeValueAsString(testUser);
		mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
		   .andExpect(status().isCreated());
	}
	
	@Test
	public void testBadPassword() throws Exception {
		testUser.setConfirmPassword("badpassword");
		String json = mapper.writeValueAsString(testUser);
		mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
		   .andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateUser() throws Exception {
		testUser.setFirstName("NewFirstName");
		String json = mapper.writeValueAsString(testUser);
		mvc.perform(put("/users/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
		  .andExpect(status().is2xxSuccessful());
		mvc.perform(get("/users/1")).andExpect(jsonPath("$.firstName", is("NewFirstName")));
	}
}
