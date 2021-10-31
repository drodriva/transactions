package com.david.transactions.rest;

import java.math.BigDecimal;
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

import com.david.transactions.model.Account;
import com.david.transactions.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Accounts API")
@RestController
@RequestMapping("v1/accounts")
public class AccountController {
	
	
	@Autowired
	private AccountService accountService;
	
	
	@Operation(summary = "Get all the accounts", description = "Returns a list with all the accounts. It can be empty")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Successful operation", 
	    content = { @Content(mediaType = "application/json", 
	      array = @ArraySchema(schema = @Schema(implementation = Account.class)))}),
	  @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
    	
    	return ResponseEntity.ok(accountService.findAllAccounts());
    }

    @Operation(summary = "Get an account by id", description = "Returns a single account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation",
                content = @Content(schema = @Schema(implementation = Account.class))),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
    	
    	return ResponseEntity.ok(accountService.getAccountById(id));
    }
    
    @Operation(summary = "Get all the given customer accounts", description = "Returns a list with all the given customer accounts")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Successful operation", 
			    content = { @Content(mediaType = "application/json", 
			      array = @ArraySchema(schema = @Schema(implementation = Account.class)))}),
			  @ApiResponse(responseCode = "404", description = "Customer not found"),
			  @ApiResponse(responseCode = "500", description = "Internal server error", 
			    content = @Content)})
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<List<Account>> getAccountsByCustomerId(@PathVariable Long customerId) {
    	
    	final List<Account> accounts = accountService.findAccountByCustomerId(customerId);
    	return ResponseEntity.ok(accounts);
    }
    
    @Operation(summary = "Get all the accounts with balance greater or equal than", description = "Returns a list with all the accounts with balance greated or equal than 'from'")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Successful operation", 
			    content = { @Content(mediaType = "application/json", 
			      array = @ArraySchema(schema = @Schema(implementation = Account.class)))}),
			  @ApiResponse(responseCode = "500", description = "Internal server error", 
			    content = @Content)})
    @GetMapping("/balances/{from}")
    public ResponseEntity<List<Account>> findByBalanceGreaterThanEqual(@PathVariable BigDecimal from) {
    	
    	final List<Account> accounts = accountService.findByBalanceGreaterThanEqual(from);
    	return ResponseEntity.ok(accounts);
    }
    
    
    @Operation(summary = "Saves a given account for a customer", description = "Receives an accounts and persists it in the database for the given customer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successful operation",
                content = @Content(schema = @Schema(implementation = Account.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @PostMapping("/{customerId}")
    public ResponseEntity<Account> saveAccount(@RequestBody Account account, @PathVariable Long customerId) {
    	
    	final Account newAccount = accountService.saveAccount(account, customerId);
    	final URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/v1/accounts/{id}")
				.buildAndExpand(newAccount.getId()).toUri();

    	return ResponseEntity.created(location).build();
    }
    
    
    @Operation(summary = "Updates a given account", description = "Receives an account and updates it in the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successful operation",
                content = @Content(schema = @Schema(implementation = Account.class))),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account, @PathVariable long id) {

    	accountService.editAccount(account, id);
    	return ResponseEntity.noContent().build();
    }
    
    
    @Operation(summary = "Deletes a given account", description = "Deletes an account by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successful operation",
                content = @Content(schema = @Schema(implementation = Account.class))),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable Long id) {
    	
    	accountService.deleteAccount(id);
    	return ResponseEntity.noContent().build();
    }

}
