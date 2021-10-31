package com.david.transactions.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;

/**
 * Represents an account transaction
 *
 */
@Entity
public class Transaction implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
    private TransactionType transactionType;
    
    private BigDecimal amount;
    
    private BigDecimal balance;
    
    private Date date;
    
    private String concept;
	
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonIgnore
    private Account account;


	public Long getId() {
		
		return id;
	}

	public void setId(Long id) {
		
		this.id = id;
	}

	public BigDecimal getAmount() {
		
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		
		this.amount = amount;
	}

	public Date getDate() {
		
		return date;
	}

	public void setDate(Date date) {
		
		this.date = date;
	}

	public Account getAccount() {
		
		return account;
	}

	public void setAccount(Account account) {
		
		this.account = account;
	}

	public TransactionType getTransactionType() {
		
		return transactionType;
	}


	public void setTransactionType(TransactionType transactionType) {
		
		this.transactionType = transactionType;
	}

	public BigDecimal getBalance() {
		
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		
		this.balance = balance;
	}

	public String getConcept() {
		
		return concept;
	}

	public void setConcept(String concept) {
		
		this.concept = concept;
	}


	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
	        return true;
	    }
	    if (!(obj instanceof Transaction)) {
	        return false;
	    }

	    final Transaction t = (Transaction) obj;

	    return Objects.equals(id, t.id) &&
	            Objects.equals(amount, t.amount) &&
	            Objects.equals(balance, t.balance) &&
	            Objects.equals(date, t.date) &&
	            Objects.equals(transactionType, t.transactionType) &&
	            Objects.equals(concept, t.concept);
	}

    @Override
    public String toString() {

        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("amount", amount)
                .add("balance", balance)
                .add("date", date)
                .add("concept", concept)
                .add("transactionType", transactionType)
                .toString();
    }
	

}
