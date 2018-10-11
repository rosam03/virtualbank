package com.demo.virtualbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.virtualbank.dao.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

}