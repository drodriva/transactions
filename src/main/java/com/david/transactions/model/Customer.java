package com.david.transactions.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;

/**
 * Represents a customer
 *
 */
@Entity
public class Customer implements Serializable {
	
	
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Long id;
	
	@Column(unique=true)
	private String customerName;
	
	@OneToMany(targetEntity=Account.class, mappedBy="customer", fetch=FetchType.LAZY, orphanRemoval = true)
	@JsonIgnore
	private List<Account> accounts;
	
	private ContactDetails contactDetails;
	

	public Long getId() {
		
		return id;
	}

	public String getCustomerName() {
		
		return customerName;
	}

	public void setId(Long id) {
		
		this.id = id;
	}

	public void setCustomerName(String customerName) {
		
		this.customerName = customerName;
	}

	public List<Account> getAccounts() {
		
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		
		this.accounts = accounts;
	}
	
	public ContactDetails getContactDetails() {
		
		return contactDetails;
	}

	public void setContactDetails(ContactDetails contactDetails) {
		
		this.contactDetails = contactDetails;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
	        return true;
	    }
	    if (!(obj instanceof Customer)) {
	        return false;
	    }

	    final Customer c = (Customer) obj;

	    return Objects.equals(id, c.id) &&
	            Objects.equals(customerName, c.customerName) &&
	            Objects.equals(contactDetails, c.contactDetails);
	}


    @Override
    public String toString() {

        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("customerName", customerName)
                .add("contactDetails", contactDetails)
                .toString();
    }

}
