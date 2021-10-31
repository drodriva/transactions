package com.david.transactions.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.david.transactions.exception.NoDataFoundException;
import com.david.transactions.model.ContactDetails;
import com.david.transactions.model.Customer;
import com.david.transactions.model.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
	
	
	@Mock
	private CustomerRepository customerRepository;
	
	@InjectMocks
	private CustomerServiceImpl customerService;
	
	
	@Test
	public void customer_findAll() {
		
		// when
		customerService.findAllCustomers();
		
		// then
		verify(customerRepository, times(1)).findAll();
	}
	
	@Test
	public void customer_notFound() {
		
		assertThrows(NoDataFoundException.class, () -> {
			customerService.getCustomerById(1L);
		  });
	}
	
	@Test
	public void customer_foundById() {
		
		// given
		final Customer c = new Customer();
		when(customerRepository.findById(1L)).thenReturn(Optional.of(c));
		
		// when
		final Customer foundCustomer = customerService.getCustomerById(1L);
		
		// then
		assertThat(foundCustomer).isEqualTo(c);
	}
	
	
	@Test
	public void customer_notFoundByName() {
		
		assertThrows(NoDataFoundException.class, () -> {
			customerService.getCustomerByCustomerName("name");
		  });
	}
	
	@Test
	public void customer_foundByName() {
		
		// given
		final Customer c = new Customer();
		final ContactDetails contactDetails = new ContactDetails();
		contactDetails.setAddress1("add1");
		contactDetails.setAddress2("add2");
		contactDetails.setCity("London");
		contactDetails.setCountry("UniteKing");
		contactDetails.setCountryCode("UK");
		contactDetails.setEmail("aa@bb.com");
		contactDetails.setPhone("123123123000");
		contactDetails.setPostCode("EEE RRR");
		contactDetails.setRegion("east");
		c.setContactDetails(contactDetails );
		final String name = "name";
		List<Customer> customerList = new ArrayList<>();
		customerList.add(c);
		when(customerRepository.findByCustomerNameIgnoreCase(name)).thenReturn(customerList);
		
		// when
		final Customer foundCustomer = customerService.getCustomerByCustomerName(name);
		
		// then
		assertThat(foundCustomer).isEqualTo(c);
	}
	
	@Test
	public void customer_saved() {
		
		//given
		final Customer c = new Customer();
		
		// when
		customerService.saveCustomer(c);
		
		// then
		verify(customerRepository, times(1)).save(c);
	}
	
	@Test
	public void customer_edited_whenFound() {
		
		//given
		final Customer c = new Customer();
		when(customerRepository.findById(1L)).thenReturn(Optional.of(c));
		
		// when
		customerService.editCustomer(c, 1L);
		
		// then
		verify(customerRepository, times(1)).save(c);
        
	}
	
	
	@Test
	public void customer_edited_notFound() {
		
		assertThrows(NoDataFoundException.class, () -> {
			customerService.editCustomer(new Customer(), 1L);
		  });
        
	}
	
	@Test
	public void customer_deleted() {
		
		// when
		customerService.deleteCustomer(1L);
		
		// then
		verify(customerRepository, times(1)).deleteById(1L);
	}

}
