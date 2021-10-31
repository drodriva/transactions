package com.david.transactions.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.david.transactions.exception.NoDataFoundException;
import com.david.transactions.model.Account;
import com.david.transactions.model.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CustomerService customerService;
	

	@Override
	public Account getAccountById(Long id) {
		
		return accountRepository.findById(id).orElseThrow(NoDataFoundException::new);
	}

	@Override
	public Account saveAccount(Account account, Long customerId) {
		
		account.setCustomer(customerService.getCustomerById(customerId));
		return accountRepository.save(account);
	}

	@Override
	public Account editAccount(Account account, Long id) {
		
		final Optional<Account> accountOptional = accountRepository.findById(id);

		if (!accountOptional.isPresent()) {
			throw new NoDataFoundException("Account id=" + id + " not found");
		}

		final Account editAccount = accountOptional.get();
		editAccount.setCreationDate(account.getCreationDate());
		editAccount.setCurrencyCode(account.getCurrencyCode());
		editAccount.setBalance(account.getBalance());
		editAccount.setDescription(account.getDescription());

		return accountRepository.save(editAccount);
	}

	@Override
	public void deleteAccount(Long id) {
		
		accountRepository.deleteById(id);
		
	}

	@Override
	public List<Account> findAccountByCustomerId(Long customerId) {
		
		List<Account> accounts = accountRepository.findByCustomerId(customerId);
		if (accounts.isEmpty()) {
			throw new NoDataFoundException("Customer id=" + customerId + " not found");
		}
		return accounts;
	}

	@Override
	public List<Account> findAllAccounts() {
		
		return accountRepository.findAll();
	}

	@Override
	public List<Account> findByBalanceGreaterThanEqual(BigDecimal balance) {
		
		return accountRepository.findByBalanceGreaterThanEqual(balance);
	}

}
