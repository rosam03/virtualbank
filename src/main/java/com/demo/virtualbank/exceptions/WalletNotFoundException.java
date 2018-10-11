package com.demo.virtualbank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class WalletNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(WalletNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String walletNotFoundHandler(WalletNotFoundException ex) {
		return ex.getMessage();
	}

}

public class WalletNotFoundException extends WalletException {

	public WalletNotFoundException(String id) {
		super("Could not find virtual wallet " + id);
	}

}