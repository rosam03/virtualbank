package com.demo.virtualbank;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.virtualbank.controller.TransactionController;
import com.demo.virtualbank.controller.WalletController;
import com.demo.virtualbank.dao.Transaction;
import com.demo.virtualbank.dao.Wallet;
import com.demo.virtualbank.exceptions.TransactionException;
import com.demo.virtualbank.exceptions.TransactionNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionControllerTest {

	@Autowired
	private TransactionController tController;

	@Autowired
	private WalletController wController;

	@Test(expected = TransactionException.class)
	public void transactionDoesntExist() throws TransactionNotFoundException {
		tController.one("12345");
	}

	@Test
	public void transactionExists() throws Exception {
		Wallet w = wController.create("UserId123");
		wController.deposit(new BigDecimal(25), w.getId());
		Transaction t = wController.lastTransactions(w.getId(), 1).get(0);
		assertEquals(tController.one(t.getId()).getId(), t.getId());
		assertTrue(t.getAmount().compareTo(new BigDecimal(25)) == 0);
	}

	@Test
	public void correctTransactionAmount() throws Exception {
		Wallet w = wController.create("UserId123");
		wController.deposit(new BigDecimal(1), w.getId());
		Transaction t = wController.lastTransactions(w.getId(), 1).get(0);
		assertTrue(t.getAmount().compareTo(new BigDecimal(1)) == 0);
	}

	@Test(expected = TransactionException.class)
	public void noTransactionsToReverse() throws Exception {
		tController.reverse("123");
	}
}