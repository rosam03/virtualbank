package com.demo.virtualbank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class InvalidWithdrawalAmountAdvice {

	@ResponseBody
	@ExceptionHandler(InvalidWithdrawalAmountException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String invalidWithdrawalAmounHandler(InvalidWithdrawalAmountException ex) {
		return ex.getMessage();
	}

}

public class InvalidWithdrawalAmountException extends WalletException {

	public InvalidWithdrawalAmountException() {
		super("Withdrawal amount must be positive");
	}

}