package com.david.transactions.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.david.transactions.exception.NoDataFoundException;
import com.david.transactions.model.Customer;
import com.david.transactions.model.CustomerRepository;

@Service
@CacheConfig(cacheNames={"customerCache"})
public class CustomerServiceImpl implements CustomerService {
	
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Cacheable
	@Override
    public Customer getCustomerById(Long id) {
		
		return customerRepository.findById(id).orElseThrow(NoDataFoundException::new);
    }
	
	@Cacheable
	@Override
	public Customer getCustomerByCustomerName(String customerName) {
		
		List<Customer> customerNames = customerRepository.findByCustomerNameIgnoreCase(customerName);
		if (customerNames.isEmpty()) {
			throw new NoDataFoundException();
		}
		return customerNames.get(0);
	}
	
	@CachePut(key = "#customer.id")
	@Override
    public Customer saveCustomer(Customer customer) {
		
        return customerRepository.save(customer);
    }

	
	@CacheEvict(key="#id")
	@Override
	public void deleteCustomer(Long id) {
		
	   customerRepository.deleteById(id);
	}

	@CachePut(key = "#id")
	@Override
	public Customer editCustomer(Customer customer, Long id) {
		
		final Optional<Customer> customerOptional = customerRepository.findById(id);

		if (!customerOptional.isPresent()) {
			throw new NoDataFoundException("Customer id=" + id + " not found");
		}

		final Customer editCustomer = customerOptional.get();
		editCustomer.setCustomerName(customer.getCustomerName());
		editCustomer.setContactDetails(customer.getContactDetails());

		return customerRepository.save(editCustomer);
	}

	@Override
	public List<Customer> findAllCustomers() {
		
		return customerRepository.findAll();
	}

}
