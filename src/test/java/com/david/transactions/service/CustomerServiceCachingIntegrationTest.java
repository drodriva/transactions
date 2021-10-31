package com.david.transactions.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.david.transactions.config.RedisConfiguration;
import com.david.transactions.model.Customer;
import com.david.transactions.model.CustomerRepository;

@Import({RedisConfiguration.class, CustomerServiceImpl.class})
@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(classes = {CacheAutoConfiguration.class})
@EnableCaching
public class CustomerServiceCachingIntegrationTest {

    private static final Long CUSTOMER_ID = 1L;
    private static final String CUSTOMER_NAME = "name";
    private static final String CACHE_NAME = "customerCache";

    @MockBean
    private CustomerRepository mockCustomerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CacheManager cacheManager;
    
    
    @BeforeEach
    void setUp() {
    	
    	cacheManager.getCache(CACHE_NAME).clear();
    }
    

    @Test
    void givenRedisCaching_whenFindCustomerById_thenCustomerReturnedFromCache() {
    	
    	// given
        final Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);
        customer.setCustomerName(CUSTOMER_NAME);
        given(mockCustomerRepository.findById(CUSTOMER_ID)).willReturn(Optional.of(customer));

        // when
        final Customer cacheMiss = customerService.getCustomerById(CUSTOMER_ID);        
        final Customer cacheHit = customerService.getCustomerById(CUSTOMER_ID);

        //then
        assertAll(
                () -> assertThat(cacheMiss).isEqualTo(customer),
                () -> assertThat(cacheHit).isEqualTo(customer),
                () -> verify(mockCustomerRepository, times(1)).findById(CUSTOMER_ID),
                () -> assertThat(objectFromCache()).isEqualTo(customer)
        );
    }
    
    
    @Test
    void givenRedisCaching_whenSaveCustomer_thenCustomerReturnedFromCachwe() {
    	
    	// given
        final Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);
        customer.setCustomerName(CUSTOMER_NAME);
        given(mockCustomerRepository.save(customer)).willReturn(customer);

        // when
        customerService.saveCustomer(customer); // puts on cache
        final Customer cacheHit = customerService.getCustomerById(CUSTOMER_ID);

        //then
        assertAll(
                () -> assertThat(cacheHit).isEqualTo(customer),
                () -> verify(mockCustomerRepository, never()).findById(CUSTOMER_ID),
                () -> assertThat(objectFromCache()).isEqualTo(customer)
        );
    }
    
   
    @Test
    void givenRedisCaching_whenEditCustomer_thenCustomerReturnedFromCache() {
    	
    	// given
        final Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);
        customer.setCustomerName(CUSTOMER_NAME);
        given(mockCustomerRepository.findById(CUSTOMER_ID)).willReturn(Optional.of(customer));
        given(mockCustomerRepository.save(customer)).willReturn(customer);

        // when
        customerService.editCustomer(customer, CUSTOMER_ID); // puts on cache but we hit db when looking for customer to edit
        final Customer cacheHit = customerService.getCustomerById(CUSTOMER_ID);

        //then
        assertAll(
                () -> assertThat(cacheHit).isEqualTo(customer),
                () -> verify(mockCustomerRepository, times(1)).findById(CUSTOMER_ID),
                () -> assertThat(objectFromCache()).isEqualTo(customer)
        );
    }
    
    
    @Test
    void givenRedisCaching_whenDeletedCustomer_thenCustomerNotInCache() {
    	
    	// given
        final Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);
        customer.setCustomerName(CUSTOMER_NAME);
        given(mockCustomerRepository.findById(CUSTOMER_ID)).willReturn(Optional.of(customer));

        // when
        customerService.getCustomerById(CUSTOMER_ID); 
        customerService.deleteCustomer(CUSTOMER_ID); 

        //then
        assertAll(
                () -> verify(mockCustomerRepository, times(1)).findById(CUSTOMER_ID),
                () -> assertThat(cacheManager.getCache(CACHE_NAME).get(CUSTOMER_ID)).isNull()
        );
    }
    

    private Object objectFromCache() {
    	
        return cacheManager.getCache(CACHE_NAME).get(CUSTOMER_ID).get();
    }


}