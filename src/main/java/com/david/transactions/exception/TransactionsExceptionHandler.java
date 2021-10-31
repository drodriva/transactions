package com.david.transactions.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class TransactionsExceptionHandler {
	
	
    @ExceptionHandler(value = NoDataFoundException.class)
    public ResponseEntity<String> noDataFoundException(NoDataFoundException noDataFoundException) {
        
    	return new ResponseEntity<>(noDataFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
    
    
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<String> dataIntegrityViolationException(DataIntegrityViolationException dataIntegrityViolationException) {
        
    	return new ResponseEntity<>(dataIntegrityViolationException.getRootCause().getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    public ResponseEntity<String> emptyResultDataAccessException(EmptyResultDataAccessException emptyResultDataAccessException) {
        
    	return new ResponseEntity<>(emptyResultDataAccessException.getMessage(), HttpStatus.NOT_FOUND);
    }
    
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> error(Exception exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
