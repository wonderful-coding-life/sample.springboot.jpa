package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.example.demo.dao.CustomerRepository;
import com.example.demo.entity.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CustomerRepository repository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void postCustomer() throws Exception {
		MvcResult result = mockMvc.perform(post("/customer?name=홍길동&address=서울시 강남구 대치1동&contact=010-1111-1111"))
			.andExpect(status().isOk())
			.andReturn();
		Customer customer = objectMapper.readValue(result.getResponse().getContentAsString(), Customer.class);
		Customer actual = repository.findById(customer.getId()).orElseThrow();
		assertThat(actual.getName()).isEqualTo("홍길동");
		assertThat(actual.getAddress()).isEqualTo("서울시 강남구 대치1동");
		assertThat(actual.getContact()).isEqualTo("010-1111-1111");
	}
	
	@Test
	public void putCustomer() throws Exception {
		MvcResult result = mockMvc.perform(post("/customer?name=홍길동&address=서울시 강남구 대치1동&contact=010-1111-1111"))
			.andExpect(status().isOk())
			.andReturn();
		Customer customer = objectMapper.readValue(result.getResponse().getContentAsString(), Customer.class);
		result = mockMvc.perform(put("/customer/" + customer.getId() + "?name=홍길철&address=서울시 강남구 대치2동&contact=010-1111-1112"))
				.andExpect(status().isOk())
				.andReturn();
		Customer actual = repository.findById(customer.getId()).orElseThrow();
		assertThat(actual.getName()).isEqualTo("홍길철");
		assertThat(actual.getAddress()).isEqualTo("서울시 강남구 대치2동");
		assertThat(actual.getContact()).isEqualTo("010-1111-1112");
	}
}
