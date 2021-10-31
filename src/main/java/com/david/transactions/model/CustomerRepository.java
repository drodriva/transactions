package com.david.transactions.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
	
	List<Customer> findAll();

	List<Customer> findByCustomerNameIgnoreCase(String userName);
}
