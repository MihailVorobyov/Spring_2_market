package com.geekbrains.geekmarketwinter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SimpleMockTesting {
	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void isLoginRequiredToAddProduct() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/products/add"))
			.andExpect(MockMvcResultMatchers.status().is(302))
			.andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost:8080/login"))
		;
	}
	
	@Test
	@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
	public void isLoggedInAdminCanAddProduct() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/products/add")).andExpect(MockMvcResultMatchers.status().is(200));
	}

	@Test
	@WithMockUser(username = "user")
	public void isLoggedInWithoutAdminRoleCannotAddProduct() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/products/add")).andExpect(MockMvcResultMatchers.status().is(403));
	}

}
