package com.example.demo.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Customer;

@SpringBootTest
public class CustomerRepositoryTests {

	@Autowired
	private CustomerRepository repository;
	
	@AfterEach
	public void afterEach() {
		repository.deleteAll();
	}
	
	@Test
	public void findByName() {
		List<Customer> customerList = repository.findByName("홍길동");
		assertThat(0).isEqualTo(customerList.size());
		
		Customer customer = Customer.builder().name("홍길동").contact("010-1111-1111").address("서울시 강남구 대치1동").build();
		repository.save(customer);
		
		customerList = repository.findByName("홍길동");
		assertThat(1).isEqualTo(customerList.size());
	}
	
	@Test
	public void findByNameLike() {
		List<Customer> customerList = repository.findByNameLike("%홍길%");
		assertThat(0).isEqualTo(customerList.size());
		
		Customer customer = Customer.builder().name("홍길동").contact("010-1111-1111").address("서울시 강남구 대치1동").build();
		repository.save(customer);
		
		customerList = repository.findByNameLike("%홍길%");
		assertThat(1).isEqualTo(customerList.size());
	}
}
