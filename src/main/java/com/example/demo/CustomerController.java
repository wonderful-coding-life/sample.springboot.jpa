package com.example.demo;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.example.demo.dao.CustomerRepository;
import com.example.demo.entity.Customer;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	private CustomerRepository repository;

	public CustomerController(CustomerRepository repository) {
		super();
		this.repository = repository;
	}
	
	@PostMapping("/")
	public Customer postCustomer(Customer customer) {
		return repository.save(customer);
	}
	
	@PutMapping("/{id}")
	public void putCustomer(@PathVariable("id") int id, Customer customer) {
		repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "no customer found"));
		customer.setId(id);
		repository.save(customer);
	}

	@DeleteMapping("/{id}")
	public void deleteCustomer(@PathVariable("id") int id) {
		repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "no customer found"));
		repository.deleteById(id);
	}
	
	@GetMapping("/{id}")
	public Customer getCustomer(@PathVariable("id") int id) {
		return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "no customer found"));
	}
	
	@GetMapping("/search")
	public List<Customer> searchCustomer(String name) {
		return repository.findByNameLike("%" + name + "%");
	}
	
	@GetMapping("/list")
	public List<Customer> getCustomerList() {
		return (List<Customer>) repository.findAll();
	}
}
