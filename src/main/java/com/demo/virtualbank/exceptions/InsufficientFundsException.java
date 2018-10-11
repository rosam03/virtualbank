package com.demo.virtualbank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class InsufficientFundsAdvice {

	@ResponseBody
	@ExceptionHandler(InsufficientFundsException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String insufficientFundsHandler(InsufficientFundsException ex) {
		return ex.getMessage();
	}

}

public class InsufficientFundsException extends WalletException {

	public InsufficientFundsException() {
		super("Withdraw amount exceeds account balance");
	}

}