package com.david.transactions.service;

import java.util.List;

import com.david.transactions.model.Customer;

public interface CustomerService {
	
	/**
	 * Finds all the {@link Customer} in the system
	 * 
	 * @return all the {@link Customer} in the system
	 */
	List<Customer> findAllCustomers();
		
	/**
	 * Gets a {@link Customer} given its id
	 * 
	 * @param id customer id
	 * 
	 * @return {@link Customer}
	 */
	Customer getCustomerById(Long id);
	
	/**
	 * Gets a {@link Customer} given its customer name
	 * 
	 * @param customerName
	 * 
	 * @return {@link Customer}
	 */
	Customer getCustomerByCustomerName(String customerName);
	
	/**
	 * Persists a given {@link Customer}
	 * 
	 * @param customer
	 * 
	 * @return the persisted {@link Customer}
	 */
	Customer saveCustomer(Customer customer);
	
	/**
	 * Updates a {@link Customer} given its id
	 * 
	 * @param customer
	 * @param id customer id
	 * 
	 * @return the updated {@link Customer}
	 */
	Customer editCustomer(Customer customer, Long id);
	
	/**
	 * Deletes a {@link Customer} given its id
	 * 
	 * @param id customer id
	 */
	void deleteCustomer(Long id);
	

}
