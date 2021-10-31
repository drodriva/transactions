package com.david.transactions.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.david.transactions.exception.NoDataFoundException;
import com.david.transactions.model.Account;
import com.david.transactions.model.AccountRepository;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
	
	
	@Mock
	private AccountRepository accountRepository;
	
	@Mock
	private CustomerService customerService;
	
	@InjectMocks
	private AccountServiceImpl accountService;
	
	
	@Test
	public void account_findAll() {
		
		// when
		accountService.findAllAccounts();
		
		// then
		verify(accountRepository, times(1)).findAll();
	}
	
	@Test
	public void account_notFoundById() {
		
		assertThrows(NoDataFoundException.class, () -> {
			accountService.getAccountById(1L);
		  });
	}
	
	@Test
	public void account_foundById() {
		
		// given
		final Account a = new Account();
		when(accountRepository.findById(1L)).thenReturn(Optional.of(a));
		
		// when
		final Account foundAccount = accountService.getAccountById(1L);
		
		// then
		assertThat(foundAccount).isEqualTo(a);
	}
	
	
	@Test
	public void account_notFoundByCustomerId() {
		
		assertThrows(NoDataFoundException.class, () -> {
			accountService.findAccountByCustomerId(1L);
		  });
	}
	
	@Test
	public void customer_foundByCustomerId() {
		
		// given
		final Long customerId = 1L;
		final Account a = new Account();
		final List<Account> accountList = new ArrayList<>();
		accountList.add(a);
		when(accountRepository.findByCustomerId(customerId)).thenReturn(accountList);
		
		// when
		final List<Account> foundAccounts = accountService.findAccountByCustomerId(customerId);
		
		// then
		assertThat(foundAccounts).contains(a);
	}
	
	@Test
	public void account_foundByBalancGreaterThanEqueal() {
		
		// given
		final BigDecimal balance = BigDecimal.valueOf(1.0);
		
		// when
		final List<Account> foundAccounts = accountService.findByBalanceGreaterThanEqual(balance);
		
		// then
		assertThat(foundAccounts).isEmpty();
	}
	
	@Test
	public void account_saved() {
		
		//given
		final Account a = new Account();
		
		// when
		accountService.saveAccount(a, 1L);
		
		// then
		verify(accountRepository, times(1)).save(a);
	}
	
	@Test
	public void account_edited_whenFound() {
		
		//given
		final Account a = new Account();
		when(accountRepository.findById(1L)).thenReturn(Optional.of(a));
		
		// when
		accountService.editAccount(a, 1L);
		
		// then
		verify(accountRepository, times(1)).save(a);
        
	}
	
	@Test
	public void account_edited_notFound() {
		
		assertThrows(NoDataFoundException.class, () -> {
			accountService.editAccount(new Account(), 1L);
		  });
        
	}
	
	@Test
	public void account_deleted() {
		
		// when
		accountService.deleteAccount(1L);
		
		// then
		verify(accountRepository, times(1)).deleteById(1L);
	}

}
