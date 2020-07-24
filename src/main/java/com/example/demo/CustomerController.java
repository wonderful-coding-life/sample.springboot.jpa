package com.example.demo;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.example.demo.dao.CustomerRepository;
import com.example.demo.entity.Customer;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	private CustomerRepository repository;

	public CustomerController(CustomerRepository repository) {
		super();
		this.repository = repository;
	}
	
	@GetMapping("")
	@ApiOperation(value = "등록된 고객 목록 조회", notes = "시스템에 등록된 모든 고객 정보를 조회합니다.\n삭제된 고객은 조회에서 제외됩니다.")
	public List<Customer> getCustomerList() {
		return (List<Customer>) repository.findAll();
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "고객 아이디를 통한 고객 조회", notes = "고객 아이디로 고객 정보를 조회합니다.\n삭제된 고객은 조회에서 제외됩니다.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "고객 정보를 정상적으로 조회하였습니다."),
			@ApiResponse(code = 404, message = "고객 아이디로 정보를 조회할 수 없습니다.\n삭제되거나 없는 고객입니다.")
	})
	public Customer getCustomer(@PathVariable("id") int id) {
		return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제되거나 없는 고객입니다"));
	}
	
	@PostMapping("")
	@ApiOperation(value = "고객 정보 생성", notes = "고객 정보를 생성합니다.\n고객 아이디가 자동으로 생성되어 부여됩니다.")
	@ApiResponse(code = 200, message = "고객 정보를 정상적으로 생성하였습니다.")
	public Customer postCustomer(
			@ApiParam(value = "고객 이름", required = true)
			@RequestParam String name,
			@ApiParam(value = "고객 주소", required = false)
			@RequestParam String address,
			@ApiParam(value = "고객 전화번호", required = true)
			@RequestParam String contact) {
		Customer customer = Customer.builder().name(name).address(address).contact(contact).build();
		return repository.save(customer);
	}
	
	@PutMapping("/{id}")
	public void putCustomer(@PathVariable("id") int id, @RequestParam String name, @RequestParam String address, @RequestParam String contact) {
		repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제되거나 없는 고객입니다"));
		Customer customer = Customer.builder().id(id).name(name).address(address).contact(contact).build();
		repository.save(customer);
	}

	@DeleteMapping("/{id}")
	public void deleteCustomer(@PathVariable("id") int id) {
		repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제되거나 없는 고객입니다"));
		repository.deleteById(id);
	}
	
	@GetMapping("/search")
	public List<Customer> searchCustomer(String name) {
		return repository.findByNameLike("%" + name + "%");
	}
}
