package com.demo.virtualbank;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.virtualbank.controller.WalletController;
import com.demo.virtualbank.dao.Transaction;
import com.demo.virtualbank.dao.Wallet;
import com.demo.virtualbank.exceptions.TransactionException;
import com.demo.virtualbank.exceptions.WalletException;
import com.demo.virtualbank.exceptions.WalletNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WalletControllerTest {

	@Autowired
	private WalletController controller;

	@Test(expected = WalletException.class)
	public void WalletDoesntExist() throws WalletNotFoundException {
		controller.one("123456");
	}

	@Test
	public void walletExists() throws WalletNotFoundException {
		Wallet w = controller.create("UserId123");
		assertEquals(w.getId(), controller.one(w.getId()).getId());
	}

	@Test
	public void walletBalanceZero() {
		Wallet w = controller.create("UserId123");
		assertEquals(w.getBalance(), new BigDecimal(0));
	}

	@Test
	public void walletDeposit() throws WalletException {
		Wallet w = controller.create("UserId123");
		controller.deposit(new BigDecimal(25), w.getId());
		assertTrue(controller.one(w.getId()).getBalance().compareTo(new BigDecimal(25)) == 0);
	}

	@Test(expected = WalletException.class)
	public void walletDepositInvalid() throws WalletException {
		Wallet w = controller.create("UserId123");
		controller.deposit(new BigDecimal(-1), w.getId());
	}

	@Test(expected = WalletException.class)
	public void walletWithdrawNoFunds() throws WalletException {
		Wallet w = controller.create("UserId123");
		controller.withdraw(new BigDecimal(25), w.getId());
	}

	@Test(expected = WalletException.class)
	public void walletWithdrawInvalid() throws WalletException {
		Wallet w = controller.create("UserId123");
		controller.withdraw(new BigDecimal(-1), w.getId());
	}

	@Test
	public void walletWithdrawSufficientFunds() throws WalletException {
		Wallet w = controller.create("UserId123");
		controller.deposit(new BigDecimal(25), w.getId());
		controller.withdraw(new BigDecimal(25), w.getId());
		assertTrue(controller.one(w.getId()).getBalance().compareTo(new BigDecimal(0)) == 0);
	}

	@Test
	public void walletGetTransactions() throws Exception {
		Wallet w = controller.create("UserId123");
		controller.deposit(new BigDecimal(25), w.getId());
		controller.deposit(new BigDecimal(7), w.getId());
		List<Transaction> list = controller.lastTransactions(w.getId(), 2);
		assertEquals(list.size(), 2);
	}

	@Test
	public void walletGetMoreTransactions() throws Exception {
		Wallet w = controller.create("UserId123");
		controller.deposit(new BigDecimal(25), w.getId());
		List<Transaction> list = controller.lastTransactions(w.getId(), 2);
		assertEquals(list.size(), 1);
	}

	@Test(expected = TransactionException.class)
	public void walletInvalidRangeTransactions() throws Exception {
		Wallet w = controller.create("UserId123");
		controller.deposit(new BigDecimal(25), w.getId());
		controller.lastTransactions(w.getId(), -1);
	}

}
