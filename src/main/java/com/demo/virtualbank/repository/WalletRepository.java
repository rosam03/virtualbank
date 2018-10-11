package com.demo.virtualbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.virtualbank.dao.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, String> {

}