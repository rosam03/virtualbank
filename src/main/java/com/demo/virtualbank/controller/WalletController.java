package com.demo.virtualbank.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.virtualbank.dao.Transaction;
import com.demo.virtualbank.dao.TransactionType;
import com.demo.virtualbank.dao.Wallet;
import com.demo.virtualbank.exceptions.InsufficientFundsException;
import com.demo.virtualbank.exceptions.InvalidDepositAmountException;
import com.demo.virtualbank.exceptions.InvalidTransactionRangeException;
import com.demo.virtualbank.exceptions.InvalidWithdrawalAmountException;
import com.demo.virtualbank.exceptions.TransactionException;
import com.demo.virtualbank.exceptions.WalletException;
import com.demo.virtualbank.exceptions.WalletNotFoundException;
import com.demo.virtualbank.repository.WalletRepository;

@RestController
public class WalletController {

	private final WalletRepository repository;

	WalletController(WalletRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/wallets/{id}")
	public Wallet one(@PathVariable String id) throws WalletNotFoundException {
		return repository.findById(id).orElseThrow(() -> new WalletNotFoundException(id));
	}

	@PostMapping("/wallets")
	public Wallet create(@RequestBody String userId) {
		Wallet wallet = new Wallet(userId);
		return repository.save(wallet);
	}

	@GetMapping("/wallets/{id}/balance")
	public BigDecimal getBalance(@PathVariable String id) throws WalletNotFoundException {
		Wallet wallet = repository.findById(id).orElseThrow(() -> new WalletNotFoundException(id));

		return wallet.getBalance();
	}

	@PutMapping("/wallets/{id}/deposit")
	public Wallet deposit(@RequestParam BigDecimal amount, @PathVariable String id) throws WalletException {

		if (amount.compareTo(new BigDecimal(0)) == -1) {
			throw new InvalidDepositAmountException(amount);
		}

		return repository.findById(id).map(Wallet -> {
			;
			Transaction t = new Transaction(amount, Wallet, TransactionType.Deposit);
			Wallet.getTransactions().add(t);
			Wallet.setBalance(Wallet.getBalance().add(amount));
			return repository.save(Wallet);
		}).orElseThrow(() -> new WalletNotFoundException(id));
	}

	@PutMapping("/wallets/{id}/withdraw")
	public Wallet withdraw(@RequestParam BigDecimal amount, @PathVariable String id) throws WalletException {

		if (amount.compareTo(new BigDecimal(0)) == -1) {
			throw new InvalidWithdrawalAmountException();
		}

		Wallet wallet = repository.findById(id).map(Wallet -> {
			;
			return Wallet;
		}).orElseThrow(() -> new WalletNotFoundException(id));

		if ((wallet.getBalance().subtract(amount)).compareTo(new BigDecimal(0)) == -1) {
			throw new InsufficientFundsException();
		}

		Transaction t = new Transaction(amount.negate(), wallet, TransactionType.Withdrawal);
		wallet.getTransactions().add(t);
		wallet.setBalance(wallet.getBalance().subtract(amount));
		return repository.save(wallet);
	}

	@GetMapping("/wallets/{id}/transactions")
	public List<Transaction> lastTransactions(@PathVariable String id, @RequestParam int limit)
			throws TransactionException, WalletException {

		if (limit <= 0) {
			throw new InvalidTransactionRangeException();
		}

		Wallet wallet = repository.findById(id).orElseThrow(() -> new WalletNotFoundException(id));

		return wallet.getNTransactions(limit);
	}

}
