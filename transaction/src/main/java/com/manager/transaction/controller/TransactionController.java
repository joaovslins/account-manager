package com.manager.transaction.controller;

import com.manager.transaction.api.V1Api;
import com.manager.transaction.dto.TransactionRequest;
import com.manager.transaction.dto.TransactionResponse;
import com.manager.transaction.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TransactionController implements V1Api {

    private final ITransactionService transactionService;

    @Override
    public ResponseEntity<TransactionResponse> addTransaction(TransactionRequest transactionRequest) {
        var response = transactionService.addTransaction(transactionRequest);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TransactionResponse> getTransactionById(Long transactionId) {
        var response = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<TransactionResponse>> listTransactions() {
        var responses = transactionService.listTransactions();
        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<List<TransactionResponse>> listTransactionsByAccount(Long accountId) {
        var responses = transactionService.listTransactionsByAccount(accountId);
        return ResponseEntity.ok(responses);
    }
}