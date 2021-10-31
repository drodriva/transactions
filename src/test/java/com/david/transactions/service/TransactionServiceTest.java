package com.david.transactions.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import com.david.transactions.exception.NoDataFoundException;
import com.david.transactions.model.Transaction;
import com.david.transactions.model.TransactionRepository;
import com.david.transactions.model.TransactionType;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
	
	
	@Mock
	private TransactionRepository transactionRepository;
	
	@Mock
	private AccountService accountService;
	
	@InjectMocks
	private TransactionServiceImpl transactionService;
	
	
	@Test
	public void transaction_findAll() {
		
		// when
		transactionService.findAllTransactions(0, 10, "date");
		
		// then
		verify(transactionRepository, times(1)).findAll(any(Pageable.class));
	}
	
	@Test
	public void transaction_notFoundById() {
		
		assertThrows(NoDataFoundException.class, () -> {
			transactionService.getTransactionById(1L);
		  });
	}
	
	@Test
	public void transaction_foundById() {
		
		// given
		final Transaction t = new Transaction();
		when(transactionRepository.findById(1L)).thenReturn(Optional.of(t));
		
		// when
		final Transaction foundTransaction = transactionService.getTransactionById(1L);
		
		// then
		assertThat(foundTransaction).isEqualTo(t);
	}
	
	@Test
	public void transaction_foundByDateBetween() {
		
		// given		
		final Date from = new Date();
		final Date to = new Date();
		
		// when
		final List<Transaction> foundTransactions = transactionService.findByDateBetween(from, to);
		
		// then
		assertThat(foundTransactions).isEmpty();
	}
	
	@Test
	public void transaction_foundByTransactionType() {
		
		// given
		final TransactionType type = TransactionType.WITHDRAWAL;
		
		// when
		final List<Transaction> foundTransactions = transactionService.findByTransactionType(type);
		
		// then
		assertThat(foundTransactions).isEmpty();
	}
	
	@Test
	public void transaction_foundByAccountId() {
		
		// given
		final Long accountId = 1L;
		final Transaction t = new Transaction();
		final List<Transaction> transactionList = new ArrayList<>();
		transactionList.add(t);
		when(transactionRepository.findByAccountId(accountId)).thenReturn(transactionList);
		
		// when
		final List<Transaction> foundTransactions = transactionService.findByAccountId(accountId);
		
		// then
		assertThat(foundTransactions).contains(t);
	}
	
	@Test
	public void account_foundByAmountGreaterThan() {
		
		// given
		final BigDecimal amount = BigDecimal.valueOf(1.0);
		
		// when
		final List<Transaction> foundTransactions = transactionService.findByAmountGreaterThan(amount);
		
		// then
		assertThat(foundTransactions).isEmpty();
	}
	
	@Test
	public void transaction_saved() {
		
		//given
		final Transaction t = new Transaction();
		
		// when
		transactionService.saveTransaction(t, 1L);
		
		// then
		verify(transactionRepository, times(1)).save(t);
	}
	
	@Test
	public void transaction_edited_whenFound() {
		
		//given
		final Transaction t = new Transaction();
		when(transactionRepository.findById(1L)).thenReturn(Optional.of(t));
		
		// when
		transactionService.editTransaction(t, 1L);
		
		// then
		verify(transactionRepository, times(1)).save(t);
        
	}
	
	@Test
	public void transaction_edited_notFound() {
		
		assertThrows(NoDataFoundException.class, () -> {
			transactionService.editTransaction(new Transaction(), 1L);
		  });
        
	}
	
	@Test
	public void transaction_deleted() {
		
		// when
		transactionService.deleteTransaction(1L);
		
		// then
		verify(transactionRepository, times(1)).deleteById(1L);
	}

}
