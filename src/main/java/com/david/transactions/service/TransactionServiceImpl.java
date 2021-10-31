package com.david.transactions.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.david.transactions.exception.NoDataFoundException;
import com.david.transactions.model.Transaction;
import com.david.transactions.model.TransactionRepository;
import com.david.transactions.model.TransactionType;

@Service
public class TransactionServiceImpl implements TransactionService {

	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private AccountService accountService;
	
	
	@Override
	public Transaction getTransactionById(Long id) {
		
		return transactionRepository.findById(id).orElseThrow(NoDataFoundException::new);
	}

	@Override
	public Transaction saveTransaction(Transaction transaction, Long accountId) {
		
		transaction.setAccount(accountService.getAccountById(accountId));
		return transactionRepository.save(transaction);
	}


	@Override
	public Transaction editTransaction(Transaction transaction, Long id) {
		
		final Optional<Transaction> transactionOtional = transactionRepository.findById(id);

		if (!transactionOtional.isPresent()) {
			throw new NoDataFoundException("Transaction id=" + id + " not found");
		}
 
		final Transaction editTransaction = transactionOtional.get();
		editTransaction.setConcept(transaction.getConcept());
		editTransaction.setTransactionType(transaction.getTransactionType());
		editTransaction.setAmount(transaction.getAmount());
		editTransaction.setDate(transaction.getDate());
		editTransaction.setBalance(transaction.getBalance());
		
		return transactionRepository.save(editTransaction);
	}

	@Override
	public void deleteTransaction(Long id) {
		
		transactionRepository.deleteById(id);
		
	}

	@Override
	public List<Transaction> findAllTransactions(Integer pageNo, Integer pageSize, String sortBy) {
		
		final Pageable pagination = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		final Page<Transaction> results = transactionRepository.findAll(pagination);
		
		return results != null && results.hasContent() ? results.getContent() : Collections.emptyList();
	}

	@Override
	public List<Transaction> findByAccountId(Long accountId) {
		
		return transactionRepository.findByAccountId(accountId);
	}

	@Override
	public List<Transaction> findByTransactionType(TransactionType transactionType) {
		
		return transactionRepository.findByTransactionType(transactionType);
	}

	@Override
	public List<Transaction> findByDateBetween(Date from, Date to) {
		
		return transactionRepository.findByDateBetween(from, to);
	}

	@Override
	public List<Transaction> findByAmountGreaterThan(BigDecimal amount) {
		
		return transactionRepository.findByAmountGreaterThan(amount);
	}

}
