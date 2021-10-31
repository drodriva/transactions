package com.david.transactions.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	List<Transaction> findAll();
	
	List<Transaction> findByAccountId(Long accountId);
	
	List<Transaction> findByTransactionType(TransactionType transactionType);
	
	List<Transaction> findByDateBetween(Date from, Date to);
	
	List<Transaction> findByAmountGreaterThan(BigDecimal amount);

}
