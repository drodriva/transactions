package com.david.transactions.service;

import java.math.BigDecimal;
import java.util.List;

import com.david.transactions.model.Account;

public interface AccountService {
	
	/**
	 * Finds all the {@link Account} in the system
	 * 
	 * @return all the {@link Account} in the system
	 */
	List<Account> findAllAccounts();
	
	/**
	 * Gets a {@link Account} given its id
	 * 
	 * @param id account id
	 * 
	 * @return {@link Account}
	 */
	Account getAccountById(Long id);
	
	/**
	 * Persists a given {@link Account} for a customer id
	 * 
	 * @param account
	 * @param customerId
	 * 
	 * @return the persisted {@link Account}
	 */
	Account saveAccount(Account account, Long customerId);
	
	/**
	 * Updates a {@link Account} given its id
	 * 
	 * @param account
	 * @param id account id
	 * 
	 * @return the updated {@link Account}
	 */
	Account editAccount(Account account, Long id);
	
	/**
	 * Deletes a {@link Account} given its id
	 * 
	 * @param id account id
	 */
	void deleteAccount(Long id);
	
	/**
	 * Gets all the {@link Account} for a given customer
	 * 
	 * @param customerId
	 * 
	 * @return all the {@link Account} for a given customer
	 */
	List<Account> findAccountByCustomerId(Long customerId);
	
	/**
	 * Gets all the {@link Account} with balance greater or equal than a given balance
	 * 
	 * @param balance
	 * 
	 * @return all the {@link Account} with balance greater or equal than a given balance
	 */
	List<Account> findByBalanceGreaterThanEqual(BigDecimal balance);
	

}
