package com.demo.virtualbank.controller;

import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.virtualbank.dao.Transaction;
import com.demo.virtualbank.dao.TransactionType;
import com.demo.virtualbank.dao.Wallet;
import com.demo.virtualbank.exceptions.TransactionException;
import com.demo.virtualbank.exceptions.TransactionNotFoundException;
import com.demo.virtualbank.exceptions.TransactionPreviouslyReversedException;
import com.demo.virtualbank.repository.TransactionRepository;

@RestController
public class TransactionController {

	private final TransactionRepository repository;

	TransactionController(TransactionRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/transactions/{id}")
	public Transaction one(@PathVariable String id) throws TransactionNotFoundException {
		return repository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));
	}

	@PostMapping("/transactions/{id}/reverse")
	public Transaction reverse(@PathVariable String id) throws TransactionException {
		Transaction original = repository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));

		if (original.isReversed()) {
			throw new TransactionPreviouslyReversedException(id);
		}

		original.setReversed(true);
		Wallet wallet = original.getWallet();
		BigDecimal amount = original.getAmount().negate();
		Transaction reversed = new Transaction(amount, wallet, TransactionType.Reversal);
		wallet.setBalance(wallet.getBalance().add(amount));

		repository.save(reversed);
		return reversed;
	}

}
