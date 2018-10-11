package com.demo.virtualbank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class TransactionPreviouslyReversedAdvice extends Exception {

	@ResponseBody
	@ExceptionHandler(TransactionPreviouslyReversedException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String transactionPreviouslyReversedHandler(TransactionPreviouslyReversedException ex) {
		return ex.getMessage();
	}

}

public class TransactionPreviouslyReversedException extends TransactionException {

	public TransactionPreviouslyReversedException(String id) {
		super("Transaction " + id + " has already been reversed");
	}

}
