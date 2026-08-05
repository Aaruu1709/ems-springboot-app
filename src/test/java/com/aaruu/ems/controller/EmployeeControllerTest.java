package com.aaruu.ems.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("dev")
//compltete springboot application..Everything becomes available.cotroller,service,repo.db
@AutoConfigureMockMvc(addFilters = false)
//Creates the MockMvc object automatically.
class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	// used to perform Http request
	@Test
	void testGetEmployeeById() throws Exception {

		mockMvc.perform(get("/employees/1"))

				.andExpect(status().isOk())

				.andExpect(jsonPath("$.message").value("Employee fetched successfully"))

				.andExpect(jsonPath("$.status").value(200))

				.andExpect(jsonPath("$.data.firstName").value("Aaru"))

				.andExpect(jsonPath("$.data.email").value("aaru@gmail.com"));

	}

	@Test
	void testSearchEmployee() throws Exception {

		mockMvc.perform(get("/employees/search").param("keyword", "Aaru"))

				.andExpect(status().isOk())

				.andExpect(jsonPath("$[0].firstName").value("Aaru"));
	}

	@Test
	void testFilterByDepartment() throws Exception {

		mockMvc.perform(get("/employees/filter").param("department", "IT"))

				.andExpect(status().isOk());

	}

	@Test
	void testPagination() throws Exception {

		mockMvc.perform(get("/employees/page").param("page", "0").param("size", "3").param("sortBy", "firstName")
				.param("direction", "asc"))

				.andExpect(status().isOk());

	}

	@Test
	void testEmployeeNotFound() throws Exception {

		mockMvc.perform(get("/employees/99999"))

				.andExpect(status().isNotFound());

	}

}

//In Unit Testing, we use Mockito and create fake data manually. In Integration Testing with MockMvc, the application uses the real Spring context and fetches actual data from the database.

//after testing i tried with docker
//so first of all what it docker what it does
//Docker is a containerization platform used to package an application along with all its dependencies, so it runs consistently in every environment.

// in my device project works fine but suppose i tried to run it with different java version, mysql version, env variables -> problem occured
//so docker help to run my application 
//java,mysql,config,dependenecied-> store into single container-> run everywhere