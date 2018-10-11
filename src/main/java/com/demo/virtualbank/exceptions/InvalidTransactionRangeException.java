package com.demo.virtualbank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class InvalidTransactionRangeAdvice extends Exception {

	@ResponseBody
	@ExceptionHandler(InvalidTransactionRangeException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String invalidTransactionRangeHandler(InvalidTransactionRangeException ex) {
		return ex.getMessage();
	}

}

public class InvalidTransactionRangeException extends TransactionException {

	public InvalidTransactionRangeException() {
		super("Transaction range must be positive");
	}

}
