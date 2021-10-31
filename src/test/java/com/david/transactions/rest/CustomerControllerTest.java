package com.david.transactions.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.david.transactions.model.Customer;
import com.david.transactions.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
	
	
	@Mock
	private CustomerService customerService;

	@InjectMocks
	private CustomerController controller;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setMessageConverters(new MappingJackson2HttpMessageConverter()).build();

	}

	@Test
	public void customers_findAllCustomers() throws Exception {

		// when
		mockMvc.perform(get("/v1/customers").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		// then
		verify(customerService, times(1)).findAllCustomers();
	}
	
	
	@Test
	public void customers_findCustomer_whenGivenId() throws Exception {
		
		// given
		final Long id = 1L;
		
		// when
		mockMvc.perform(get("/v1/customers/{id}", id).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		// then
		verify(customerService, times(1)).getCustomerById(id);
	}
	
	
	@Test
	public void customers_findCustomer_whenGivenName() throws Exception {
		
		// given
		final String name = "name";
		when(customerService.getCustomerByCustomerName(name)).thenReturn(new Customer());
		
		// when
		mockMvc.perform(get("/v1/customers/names/{customerName}", name).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		// then
		verify(customerService, times(1)).getCustomerByCustomerName(name);
	}
	
	
	@Test
	public void customer_created_whenPostRequest() throws Exception {
		
		// given
		when(customerService.saveCustomer(any())).thenReturn(new Customer());
		final ObjectMapper serialMapper = new ObjectMapper();
		final String s = serialMapper.writeValueAsString(new Customer());
		
		// when
		mockMvc.perform(post("/v1/customers")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(s)
          .accept(MediaType.APPLICATION_JSON))
          .andDo(print())
          .andExpect(status().is2xxSuccessful())
          .andReturn();

		// then
		verify(customerService, times(1)).saveCustomer(any(Customer.class));
	}
	
	
	@Test
	public void customer_edited_whenPutRequest() throws Exception {
		
		// given
		Long id = 1L;
		when(customerService.editCustomer(any(), eq(id))).thenReturn(new Customer());
		final ObjectMapper serialMapper = new ObjectMapper();
		final String s = serialMapper.writeValueAsString(new Customer());
		
		// when
		mockMvc.perform(put("/v1/customers/{id}", id)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(s)
          .accept(MediaType.APPLICATION_JSON))
          .andDo(print())
          .andExpect(status().is2xxSuccessful())
          .andReturn();

		// then
		verify(customerService, times(1)).editCustomer(any(Customer.class), eq(id));
	}
	
	
	@Test
	public void customer_deleted_whenDeleteRequest() throws Exception {
		
		// given
		final Long id = 1L;
		
		// when
		mockMvc.perform(delete("/v1/customers/{id}", id)
                  .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON))
          .andDo(print())
          .andExpect(status().is2xxSuccessful())
          .andReturn();

		// then
		verify(customerService, times(1)).deleteCustomer(id);
	}

}
