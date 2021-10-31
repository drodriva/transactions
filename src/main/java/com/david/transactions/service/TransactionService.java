package com.david.transactions.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.david.transactions.model.Transaction;
import com.david.transactions.model.TransactionType;

public interface TransactionService {
	
	/**
	 * Finds all the {@link Transaction} in the system
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param sortBy
	 * 
	 * @return all the {@link Transaction} in the system
	 */
	List<Transaction> findAllTransactions(Integer pageNo, Integer pageSize, String sortBy);
	
	/**
	 * Finds all the {@link Transaction} for a given account id
	 * 
	 * @param accountId
	 * 
	 * @return all the {@link Transaction} for a given account id
	 */
	List<Transaction> findByAccountId(Long accountId);
	
	/**
	 * Finds all the {@link Transaction} for a given transaction type
	 * 
	 * @param transactionType
	 * 
	 * @return all the {@link Transaction} for a given transaction type
	 */
	List<Transaction> findByTransactionType(TransactionType transactionType);
	
	/**
	 * Finds all the {@link Transaction} between from and to dates
	 * 
	 * @param from
	 * @param to
	 * 
	 * @return all the {@link Transaction} between from and to dates
	 */
	List<Transaction> findByDateBetween(Date from, Date to);
	
	/**
	 * Finds all the {@link Transaction} with greater amount than given 'amount'
	 * 
	 * @param amount
	 * 
	 * @return all the {@link Transaction} with greater amount than given 'amount'
	 */
	List<Transaction> findByAmountGreaterThan(BigDecimal amount);
	
	/**
	 * Gets a {@link Transaction} given its id
	 * 
	 * @param id account id
	 * 
	 * @return {@link Transaction}
	 */
	Transaction getTransactionById(Long id);
	
	/**
	 * Persists a given {@link Transaction} for an account id
	 * 
	 * @param transaction
	 * @param accountId
	 * 
	 * @return the persisted {@link Transaction}
	 */
	Transaction saveTransaction(Transaction transaction, Long accountId);
	
	/**
	 * Updates a {@link Transaction} given its id
	 * 
	 * @param transaction
	 * @param id transaction id
	 * 
	 * @return the updated {@link Transaction}
	 */
	Transaction editTransaction(Transaction transaction, Long id);
	
	/**
	 * Deletes a {@link Transaction} given its id
	 * 
	 * @param id transaction id
	 */
	void deleteTransaction(Long id);
	

}
