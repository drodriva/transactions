package com.david.transactions.model;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long>  {
	
	List<Account> findAll();
	
	List<Account> findByCustomerId(Long customerId);
	
	List<Account> findByBalanceGreaterThanEqual(BigDecimal balance);

}
