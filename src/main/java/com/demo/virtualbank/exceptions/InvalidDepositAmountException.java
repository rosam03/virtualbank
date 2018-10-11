package com.demo.virtualbank.exceptions;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class InvalidDepositAmountAdvice {

	@ResponseBody
	@ExceptionHandler(InvalidDepositAmountException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String invalidDepositAmountHandler(InvalidDepositAmountException ex) {
		return ex.getMessage();
	}

}

public class InvalidDepositAmountException extends WalletException {

	public InvalidDepositAmountException(BigDecimal amount) {
		super("Deposit amount " + amount + " must be greater than 0");
	}

}