package com.theironyard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theironyard.entities.User;
import com.theironyard.services.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegistrationFormSpringApplicationTests {

	@Autowired
	UserRepository users;

	@Autowired
	WebApplicationContext wac;

	MockMvc mockMvc;

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void aAddUser() throws Exception {
		User user = new User("Alice","Hello St","alice@gmail.com");
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(user);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/user")
					.content(json)
					.contentType("application/json")
		);
		Assert.assertTrue(users.count() == 1);
	}

	@Test
	public void bEditUser() throws Exception {
		User user = users.findAll().iterator().next();
		user.setAddress("Charleston");
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(user);
		mockMvc.perform(
				MockMvcRequestBuilders.put("/user")
					.content(json)
					.contentType("application/json")
		);
		user = users.findAll().iterator().next();
		Assert.assertTrue(user.getAddress().equals("Charleston"));
	}

	@Test
	public void cGetUsers() throws Exception {
		ResultActions ra = mockMvc.perform(
				MockMvcRequestBuilders.get("/user")
		);
		MvcResult result = ra.andReturn();
		MockHttpServletResponse response = result.getResponse();
		String content = response.getContentAsString();
		ObjectMapper om = new ObjectMapper();
		ArrayList users = om.readValue(content,ArrayList.class);
		Assert.assertTrue(users.size() == 1);
	}

	@Test
	public void dDeleteUser() throws Exception {
		User user = users.findAll().iterator().next();
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/user/" + user.getId())
		);
		Assert.assertTrue(users.count() == 0);
	}
}
