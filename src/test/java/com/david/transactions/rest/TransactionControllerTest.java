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
import java.util.Date;

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

import com.david.transactions.model.Transaction;
import com.david.transactions.model.TransactionType;
import com.david.transactions.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {
	
	@Mock
	private TransactionService transactionService;

	@InjectMocks
	private TransactionController controller;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setMessageConverters(new MappingJackson2HttpMessageConverter()).build();

	}

	@Test
	public void transactions_findAllTransactions() throws Exception {

		// when
		mockMvc.perform(get("/v1/transactions").param("pageNo", "0").param("pageSize", "2").param("sortBy", "date")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andReturn();

		// then
		verify(transactionService, times(1)).findAllTransactions(eq(0), eq(2), eq("date"));
	}
	
	
	@Test
	public void transactions_findTransaction_whenGivenId() throws Exception {
		
		// given
		final Long id = 1L;
		
		// when
		mockMvc.perform(get("/v1/transactions/{id}", id).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		// then
		verify(transactionService, times(1)).getTransactionById(id);
	}
	
	
	@Test
	public void transactions_findTransaction_whenGivenType() throws Exception {
		
		// given
		final TransactionType type = TransactionType.DEPOSIT;
		
		// when
		mockMvc.perform(get("/v1/transactions/types/{transactionType}", type).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		// then
		verify(transactionService, times(1)).findByTransactionType(type);
	}
	
	@Test
	public void transactions_findTransaction_whenGivenAccountId() throws Exception {
		
		// given
		final Long id = 1L;
		
		// when
		mockMvc.perform(get("/v1/transactions/accounts/{accountId}", id).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		// then
		verify(transactionService, times(1)).findByAccountId(id);
	}
	
	
	@Test
	public void transactions_findTransaction_greaterThanAmount() throws Exception {
		
		// given
		final BigDecimal amount = BigDecimal.valueOf(1L);
		
		// when
		mockMvc.perform(get("/v1/transactions/amounts/from/{amount}", amount).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		// then
		verify(transactionService, times(1)).findByAmountGreaterThan(amount);
	}
	
	
	@Test
	public void transactions_findTransaction_fromToDate() throws Exception {
		
		// given
		final long from = 4587122L;
		final long to = 99999999L;
		
		// when
		mockMvc.perform(get("/v1/transactions/{from}/to/{to}", from, to).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();

		// then
		verify(transactionService, times(1)).findByDateBetween(any(Date.class), any(Date.class));
	}
	
	
	@Test
	public void transaction_createdForAccountId_whenPostRequest() throws Exception {
		
		// given
		final Long id = 1L;
		when(transactionService.saveTransaction(any(), eq(id))).thenReturn(new Transaction());
		final ObjectMapper serialMapper = new ObjectMapper();
		final String s = serialMapper.writeValueAsString(new Transaction());
		
		// when
		mockMvc.perform(post("/v1/transactions/{accountId}", id)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(s)
          .accept(MediaType.APPLICATION_JSON))
          .andDo(print())
          .andExpect(status().is2xxSuccessful())
          .andReturn();

		// then
		verify(transactionService, times(1)).saveTransaction(any(Transaction.class), eq(id));
	}
	
	
	@Test
	public void transaction_edited_whenPutRequest() throws Exception {
		
		// given
		Long id = 1L;
		when(transactionService.editTransaction(any(), eq(id))).thenReturn(new Transaction());
		final ObjectMapper serialMapper = new ObjectMapper();
		final String s = serialMapper.writeValueAsString(new Transaction());
		
		// when
		mockMvc.perform(put("/v1/transactions/{id}", id)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(s)
          .accept(MediaType.APPLICATION_JSON))
          .andDo(print())
          .andExpect(status().is2xxSuccessful())
          .andReturn();

		// then
		verify(transactionService, times(1)).editTransaction(any(Transaction.class), eq(id));
	}
	
	
	@Test
	public void transaction_deleted_whenDeleteRequest() throws Exception {
		
		// given
		final Long id = 1L;
		
		// when
		mockMvc.perform(delete("/v1/transactions/{id}", id)
                  .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON))
          .andDo(print())
          .andExpect(status().is2xxSuccessful())
          .andReturn();

		// then
		verify(transactionService, times(1)).deleteTransaction(id);
	}

}
