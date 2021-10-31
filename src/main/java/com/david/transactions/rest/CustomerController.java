package com.david.transactions.rest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.david.transactions.model.Customer;
import com.david.transactions.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@Tag(name = "Customers API")
@RestController
@RequestMapping("v1/customers")
public class CustomerController {
	
	
	@Autowired
	private CustomerService customerService;
	
	
	@Operation(summary = "Get all the customers", description = "Returns a list with all the customers. It can be empty")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Successful operation", 
	    content = { @Content(mediaType = "application/json", 
	      array = @ArraySchema(schema = @Schema(implementation = Customer.class)))}),
	  @ApiResponse(responseCode = "500", description = "Internal server error", 
	    content = @Content)})
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
    	
    	return ResponseEntity.ok(customerService.findAllCustomers());
    }
	
	
    @Operation(summary = "Get a customer by id", description = "Returns a single customer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation",
                content = @Content(schema = @Schema(implementation = Customer.class))),
        @ApiResponse(responseCode = "404", description = "Customer not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
    	
    	return ResponseEntity.ok(customerService.getCustomerById(id));
    }
    
    
    @Operation(summary = "Get a customer by name", description = "Returns a single customer by name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation",
                content = @Content(schema = @Schema(implementation = Customer.class))),
        @ApiResponse(responseCode = "404", description = "Customer not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @GetMapping("/names/{customerName}")
    public ResponseEntity<Customer> getCustomerByCustomerName(@PathVariable String customerName) {
    	
    	final Customer foundCustomer = customerService.getCustomerByCustomerName(customerName);
    	return ResponseEntity.ok(foundCustomer);
    }
    
    
    @Operation(summary = "Save a given customer", description = "Receives a customer and persists it in the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successful operation",
                content = @Content(schema = @Schema(implementation = Customer.class))),
        @ApiResponse(responseCode = "400", description = "Duplicated customer"),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
    	
    	Customer newCustomer = customerService.saveCustomer(customer);
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
    			.buildAndExpand(newCustomer.getId()).toUri();

    	return ResponseEntity.created(location).build();
    }
    
    
    @Operation(summary = "Update a given customer", description = "Receives a customer and updates it in the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successful operation",
                content = @Content(schema = @Schema(implementation = Customer.class))),
        @ApiResponse(responseCode = "400", description = "Duplicated customer"),
        @ApiResponse(responseCode = "404", description = "Customer not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable long id) {

    	customerService.editCustomer(customer, id);
    	return ResponseEntity.noContent().build();
    }
    
    
    @Operation(summary = "Deletes a given customer", description = "Deletes a customer by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successful operation",
                content = @Content(schema = @Schema(implementation = Customer.class))),
        @ApiResponse(responseCode = "404", description = "Customer not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
    	
    	customerService.deleteCustomer(id);
    	return ResponseEntity.noContent().build();
    }

}
