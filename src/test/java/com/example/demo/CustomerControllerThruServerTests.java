package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import com.example.demo.dao.CustomerRepository;
import com.example.demo.entity.Customer;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerControllerThruServerTests {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private CustomerRepository repository;
	
	@Test
	public void postCustomer() throws Exception {
		Customer customer = restTemplate.postForObject("http://localhost:" + port + "/customer?name=홍길동&address=서울시 강남구 대치1동&contact=010-1111-1111", null, Customer.class);
		Customer actual = repository.findById(customer.getId()).orElseThrow(() -> new IllegalStateException());
		assertThat(actual.getName()).isEqualTo("홍길동");
		assertThat(actual.getAddress()).isEqualTo("서울시 강남구 대치1동");
		assertThat(actual.getContact()).isEqualTo("010-1111-1111");
	}
	
	@Test
	public void putCustomer() throws Exception {
		Customer customer = restTemplate.postForObject("http://localhost:" + port + "/customer?name=홍길동&address=서울시 강남구 대치1동&contact=010-1111-1111", null, Customer.class);
		restTemplate.put("http://localhost:" + port + "/customer/" + customer.getId() + "?name=홍길철&address=서울시 강남구 대치2동&contact=010-1111-1112", null);
		Customer actual = repository.findById(customer.getId()).orElseThrow(() -> new IllegalStateException());
		assertThat(actual.getName()).isEqualTo("홍길철");
		assertThat(actual.getAddress()).isEqualTo("서울시 강남구 대치2동");
		assertThat(actual.getContact()).isEqualTo("010-1111-1112");
	}
}
