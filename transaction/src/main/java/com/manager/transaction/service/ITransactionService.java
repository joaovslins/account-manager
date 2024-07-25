package com.manager.transaction.service;

import com.manager.transaction.dto.TransactionRequest;
import com.manager.transaction.dto.TransactionResponse;

import java.util.List;

public interface ITransactionService {

    TransactionResponse addTransaction(TransactionRequest transactionRequest);

    TransactionResponse getTransactionById(Long transactionId);

    List<TransactionResponse> listTransactions();

    List<TransactionResponse> listTransactionsByAccount(Long accountId);
}
