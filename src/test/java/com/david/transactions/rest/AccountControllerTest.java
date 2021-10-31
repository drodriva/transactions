package com.david.transactions.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

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

import com.david.transactions.model.Account;
import com.david.transactions.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

	@Mock
	private AccountService accountService;

	@InjectMocks
	private AccountController controller;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setMessageConverters(new MappingJackson2HttpMessageConverter()).build();

	}

	@Test
	public void accounts_findAllAccounts() throws Exception {

		// when
		mockMvc.perform(get("/v1/accounts").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		// then
		verify(accountService, times(1)).findAllAccounts();
	}
	
	
	@Test
	public void accounts_findAccount_whenGivenId() throws Exception {
		
		// given
		final Long id = 1L;
		
		// when
		mockMvc.perform(get("/v1/accounts/{id}", id).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		// then
		verify(accountService, times(1)).getAccountById(id);
	}
	
	
	@Test
	public void accounts_findAccountNotFound_whenCustomerId() throws Exception {
		
		// given
		final Long id = 1L;
		
		// when
		mockMvc.perform(get("/v1/accounts/customers/{customerId}", id).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		// then
		verify(accountService, times(1)).findAccountByCustomerId(id);
	}
	
	
	@Test
	public void accounts_findAccount_whenGivenBalance() throws Exception {
		
		// given
		final BigDecimal id = BigDecimal.valueOf(1L);
		
		// when
		mockMvc.perform(get("/v1/accounts/balances/{from}", id).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		// then
		verify(accountService, times(1)).findByBalanceGreaterThanEqual(id);
	}
	
	
	@Test
	public void account_createdForCustomerId_whenPostRequest() throws Exception {
		
		// given
		final Long id = 1L;
		when(accountService.saveAccount(any(), eq(id))).thenReturn(new Account());
		final ObjectMapper serialMapper = new ObjectMapper();
		final String s = serialMapper.writeValueAsString(new Account());
		
		// when
		mockMvc.perform(post("/v1/accounts/{customerId}", id)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(s)
          .accept(MediaType.APPLICATION_JSON))
          .andDo(print())
          .andExpect(status().is2xxSuccessful())
          .andReturn();

		// then
		verify(accountService, times(1)).saveAccount(any(Account.class), eq(id));
	}
	
	
	@Test
	public void account_edited_whenPutRequest() throws Exception {
		
		// given
		Long id = 1L;
		when(accountService.editAccount(any(), eq(id))).thenReturn(new Account());
		final ObjectMapper serialMapper = new ObjectMapper();
		final String s = serialMapper.writeValueAsString(new Account());
		
		// when
		mockMvc.perform(put("/v1/accounts/{id}", id)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(s)
          .accept(MediaType.APPLICATION_JSON))
          .andDo(print())
          .andExpect(status().is2xxSuccessful())
          .andReturn();

		// then
		verify(accountService, times(1)).editAccount(any(Account.class), eq(id));
	}
	
	
	@Test
	public void account_deleted_whenDeleteRequest() throws Exception {
		
		// given
		final Long id = 1L;
		
		// when
		mockMvc.perform(delete("/v1/accounts/{id}", id)
                  .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON))
          .andDo(print())
          .andExpect(status().is2xxSuccessful())
          .andReturn();

		// then
		verify(accountService, times(1)).deleteAccount(id);
	}
}
