package com.demo.virtualbank.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "wallets")
public class Wallet {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "wallet_id", nullable = false, columnDefinition = "CHAR(32)")
	private String walletId;

	@OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonBackReference
	private List<Transaction> transactions;

	@Column(nullable = false)
	private BigDecimal balance;

	@Column(name = "user_id")
	private String userId;

	public Wallet() {
		this.balance = new BigDecimal(0);
		;
		this.transactions = new ArrayList<>();
	}

	public Wallet(String userId) {
		this.balance = new BigDecimal(0);
		this.userId = userId;
		this.transactions = new ArrayList<>();
	}

	public String getId() {
		return walletId;
	}

	public void setId(String id) {
		this.walletId = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String id) {
		this.userId = id;
	}

	@Column(precision = 19, scale = 2)
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public List<Transaction> getNTransactions(int n) {

		if (n >= transactions.size()) {
			return transactions;
		}

		return transactions.subList(0, n);
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

}