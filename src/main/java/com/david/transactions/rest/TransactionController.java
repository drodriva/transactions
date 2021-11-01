package com.david.transactions.rest;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.david.transactions.model.Transaction;
import com.david.transactions.model.TransactionType;
import com.david.transactions.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Transactions API")
@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	

	@Operation(summary = "Get all the transactions", description = "Returns a list with all the transactions. It can be paginated and the results sorted")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successful operation", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Transaction.class)))}),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
	@GetMapping
	public ResponseEntity<List<Transaction>> findAllTransactions(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "100") Integer pageSize, @RequestParam(defaultValue = "date") String sortBy) {

		return ResponseEntity.ok(transactionService.findAllTransactions(pageNo, pageSize, sortBy));
	}
	
    @Operation(summary = "Get a transaction by id", description = "Returns a single transaction by given id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation",
                content = @Content(schema = @Schema(implementation = Transaction.class))),
        @ApiResponse(responseCode = "404", description = "Transaction not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
    	
    	return ResponseEntity.ok(transactionService.getTransactionById(id));
    }
    
    @Operation(summary = "Get all the transactions from a given type", description = "Returns a list with all the transactions for a given type")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Successful operation", 
			    content = { @Content(mediaType = "application/json", 
			      array = @ArraySchema(schema = @Schema(implementation = Transaction.class)))}),
			  @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @GetMapping("/types/{transactionType}")
    public ResponseEntity<List<Transaction>> getTransactionByTransactionType(@PathVariable TransactionType transactionType) {
    	
    	return ResponseEntity.ok(transactionService.findByTransactionType(transactionType));
    }
    
    
    @Operation(summary = "Get all the transactions with amount greater than", description = "Returns a list with all the transactions with amount greater than 'amount'")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Successful operation", 
			    content = { @Content(mediaType = "application/json", 
			      array = @ArraySchema(schema = @Schema(implementation = Transaction.class)))}),
			  @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @GetMapping("/amounts/from/{amount}")
    public ResponseEntity<List<Transaction>> getTransactionByAmountGreaterThan(@PathVariable BigDecimal amount) {
    	
    	return ResponseEntity.ok(transactionService.findByAmountGreaterThan(amount));
    }
    
    @Operation(summary = "Get all the transactions between dates", description = "Returns a list with all the transactions with date between given values")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Successful operation", 
			    content = { @Content(mediaType = "application/json", 
			      array = @ArraySchema(schema = @Schema(implementation = Transaction.class)))}),
			  @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @GetMapping("/{from}/to/{to}")
	public ResponseEntity<List<Transaction>> getTransactionByDateBetween(@PathVariable Long from, @PathVariable Long to) {
    	
    	return ResponseEntity.ok(transactionService.findByDateBetween(new Date(from), new Date(to)));
    }
    
    @Operation(summary = "Get all the given account transactions", description = "Returns a list with all the given account transactions")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Successful operation", 
			    content = { @Content(mediaType = "application/json", 
			      array = @ArraySchema(schema = @Schema(implementation = Transaction.class)))}),
			  @ApiResponse(responseCode = "404", description = "Account not found"),
			  @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<List<Transaction>> getAccountTransactions(@PathVariable Long accountId) {
    	
    	final List<Transaction> transactions = transactionService.findByAccountId(accountId);
    	return ResponseEntity.ok(transactions);
    }
    
    
    @Operation(summary = "Saves a given transaction for an account", description = "Receives a transaction and persists it in the database for the given account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successful operation",
                content = @Content(schema = @Schema(implementation = Transaction.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @PostMapping("/{accountId}")
    public ResponseEntity<Transaction> saveTransaction(@RequestBody Transaction transaction, @PathVariable Long accountId) {
    	
    	final Transaction newTransaction = transactionService.saveTransaction(transaction, accountId);
		final URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/v1/transactions/{id}")
				.buildAndExpand(newTransaction.getId()).toUri();

    	return ResponseEntity.created(location).build();
    }
    
    
    @Operation(summary = "Updates a given transaction", description = "Receives a transactions and updates it in the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successful operation",
                content = @Content(schema = @Schema(implementation = Transaction.class))),
        @ApiResponse(responseCode = "404", description = "Transaction not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction, @PathVariable long id) {

    	transactionService.editTransaction(transaction, id);
    	return ResponseEntity.noContent().build();
    }
    
    
    @Operation(summary = "Deletes a given transaction", description = "Deletes a transaction by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successful operation",
                content = @Content(schema = @Schema(implementation = Transaction.class))),
        @ApiResponse(responseCode = "404", description = "Transaction not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction> deleteTransaction(@PathVariable Long id) {
    	
    	transactionService.deleteTransaction(id);
    	return ResponseEntity.noContent().build();
    }

}
