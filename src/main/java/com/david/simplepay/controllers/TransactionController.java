package com.david.simplepay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david.simplepay.entities.Transaction;
import com.david.simplepay.dtos.TransactionDTO;
import com.david.simplepay.services.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transactionDTO) throws Exception {

        Transaction newTransaction = transactionService.createTransaction(transactionDTO);
        return new ResponseEntity<>(newTransaction, HttpStatus.OK);
    }
}
