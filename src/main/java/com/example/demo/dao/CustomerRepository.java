package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
	List<Customer> findByName(String name);
	List<Customer> findByNameLike(String name);
	List<Customer> findByNameLikeOrderByAddressDesc(String name);
	List<Customer> findByNameOrAddress(String name, String address);
	
	@Query("from Customer where name = ?1 and primaryContact = ?2")
	List<Customer> findVipList(String value1, String value2);
	
	@Query(value="select * from Customer where name = ?1 and primary_contact = ?2", nativeQuery=true)
	List<Customer> findVipList2(String value1, String value2);
}
