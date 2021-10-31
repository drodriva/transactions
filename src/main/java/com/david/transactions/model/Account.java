package com.david.transactions.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;

/**
 * Represents an Account
 *
 */
@Entity
public class Account implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
    private Long id;
	
    private String description;
    
    private String currencyCode;
    
    private Date creationDate;
    
    private BigDecimal balance;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;
    
	@OneToMany(targetEntity=Transaction.class, mappedBy="account", fetch=FetchType.LAZY, orphanRemoval = true)
	@JsonIgnore
	private List<Transaction> transactions;
    

    public Customer getCustomer() {
    	
        return customer;
    }
    
	public void setCustomer(Customer customer) {
		
		this.customer = customer;
	}

	public Long getId() {
		
		return id;
	}
	
	public void setId(Long id) {
		
		this.id = id;
	}
	
	public BigDecimal getBalance() {
		
		return balance;
	}
	
	public void setBalance(BigDecimal balance) {
		
		this.balance = balance;
	}

	public String getDescription() {
		
		return description;
	}

	public void setDescription(String description) {
		
		this.description = description;
	}

	public String getCurrencyCode() {
		
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		
		this.currencyCode = currencyCode;
	}

	public Date getCreationDate() {
		
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		
		this.creationDate = creationDate;
	}

	public List<Transaction> getTransactions() {
		
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		
		this.transactions = transactions;
	}


	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
	        return true;
	    }
	    if (!(obj instanceof Account)) {
	        return false;
	    }

	    final Account a = (Account) obj;

	    return Objects.equals(id, a.id) &&
	            Objects.equals(description, a.description) &&
	            Objects.equals(creationDate, a.creationDate) &&
	            Objects.equals(balance, a.balance) &&
	            Objects.equals(currencyCode, a.currencyCode);
	}


    @Override
    public String toString() {

        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("description", description)
                .add("creationDate", creationDate)
                .add("balance", balance)
                .add("currencyCode", currencyCode)
                .toString();
    }
    
}
