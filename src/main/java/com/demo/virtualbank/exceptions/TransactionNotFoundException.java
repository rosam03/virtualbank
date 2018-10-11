package com.demo.virtualbank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class TransactionNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(TransactionNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String transactionNotFoundHandler(TransactionNotFoundException ex) {
		return ex.getMessage();
	}

}

public class TransactionNotFoundException extends TransactionException {

	public TransactionNotFoundException(String id) {
		super("Could not find transaction " + id);
	}

}