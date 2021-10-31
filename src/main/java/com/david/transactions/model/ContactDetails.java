package com.david.transactions.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

import com.google.common.base.MoreObjects;

/**
 * Customer contact details
 *
 */
@Embeddable
public class ContactDetails implements Serializable {


	private static final long serialVersionUID = 1L;
	
    private String address1;
    
    private String address2;
    
    private String email;
    
    private String phone;
    
    private String country;
    
    private String countryCode;
    
    private String postCode;
    
    private String region;
    
    private String city;
    
    
	public String getAddress1() {
		
		return address1;
	}
	
	public void setAddress1(String address1) {
		
		this.address1 = address1;
	}
	
	public String getAddress2() {
		
		return address2;
	}
	
	public void setAddress2(String address2) {
		
		this.address2 = address2;
	}

	public String getEmail() {
		
		return email;
	}
	
	public void setEmail(String email) {
		
		this.email = email;
	}
	
	public String getPhone() {
		
		return phone;
	}
	
	public void setPhone(String phone) {
		
		this.phone = phone;
	}
	
	public String getCountry() {
		
		return country;
	}
	
	public void setCountry(String country) {
		
		this.country = country;
	}
	
	public String getCountryCode() {
		
		return countryCode;
	}
	
	public void setCountryCode(String countryCode) {
		
		this.countryCode = countryCode;
	}
	
	public String getPostCode() {
		
		return postCode;
	}
	
	public void setPostCode(String postCode) {
		
		this.postCode = postCode;
	}
	
	public String getRegion() {
		
		return region;
	}
	
	public void setRegion(String region) {
		
		this.region = region;
	}
	
	public String getCity() {
		
		return city;
	}
	
	public void setCity(String city) {
		
		this.city = city;
	}
    
    
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
	        return true;
	    }
	    if (!(obj instanceof ContactDetails)) {
	        return false;
	    }

	    final ContactDetails cd = (ContactDetails) obj;

	    return Objects.equals(address1, cd.address1) &&
	            Objects.equals(address2, cd.address2) &&
	            Objects.equals(email, cd.email) &&
	            Objects.equals(phone, cd.phone) &&
	            Objects.equals(postCode, cd.postCode) &&
	            Objects.equals(region, cd.region) &&
	            Objects.equals(countryCode, cd.countryCode) &&
	            Objects.equals(country, cd.country) &&
	            Objects.equals(city, cd.city);
	}


    @Override
    public String toString() {

        return MoreObjects.toStringHelper(this)
                .add("address1", address1)
                .add("address2", address2)
                .add("email", email)
                .add("phone", phone)
                .add("country", country)
                .add("countryCode", countryCode)
                .add("postCode", postCode)
                .add("region", region)
                .add("city", city)
                .toString();
    }

}
