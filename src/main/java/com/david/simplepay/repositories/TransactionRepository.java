package com.david.simplepay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.david.simplepay.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
