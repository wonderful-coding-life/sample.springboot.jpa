package com.example.demo;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.example.demo.dao.CustomerRepository;
import com.example.demo.entity.Customer;

@RestController
public class CustomerController {

	private CustomerRepository repository;

	public CustomerController(CustomerRepository repository) {
		super();
		this.repository = repository;
	}
	
	@PostMapping("/customer")
	public Customer postCustomer(Customer customer) {
		return repository.save(customer);
	}
	
	@PutMapping("/customer/{id}")
	public void putCustomer(@PathVariable("id") int id, Customer customer) {
		repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "no customer found"));
		customer.setId(id);
		repository.save(customer);
	}

	@DeleteMapping("/customer/{id}")
	public void deleteCustomer(@PathVariable("id") int id) {
		repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "no customer found"));
		repository.deleteById(id);
	}
	
	@GetMapping("/customer/{id}")
	public Customer getCustomer(@PathVariable("id") int id) {
		return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "no customer found"));
	}
	
	@GetMapping("/customer/search")
	public List<Customer> searchCustomer(String name) {
		return repository.findByNameLike("%" + name + "%");
	}
	
	@GetMapping("/customer/list")
	public List<Customer> getCustomerList() {
		return (List<Customer>) repository.findAll();
	}
}
