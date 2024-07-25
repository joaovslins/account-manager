package com.manager.transaction.service.impl;

import com.manager.transaction.consumer.WalletConsumer;
import com.manager.transaction.consumer.dto.FinancialValue;
import com.manager.transaction.dto.TransactionRequest;
import com.manager.transaction.dto.TransactionResponse;
import com.manager.transaction.entity.TransactionEntity;
import com.manager.transaction.exception.CustomValidatorException;
import com.manager.transaction.mapper.Mapper;
import com.manager.transaction.repository.TransactionRepository;
import com.manager.transaction.service.ITransactionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletConsumer walletConsumer;

    @Override
    public TransactionResponse addTransaction(TransactionRequest transactionRequest) {
        try {
            if (isDuplicateTransaction(transactionRequest)) {
                throw new Exception("Duplicate transaction");
            }

            var wallet = walletConsumer.getWallet(Integer.parseInt(transactionRequest.getAccount().toString()));

            if (wallet == null || wallet.getStatus() == "BLOCKED") {
                throw new Exception("Wallet blocked or not found");
            }

            var entity = Mapper.map(transactionRequest, TransactionEntity.class);

            switch (getOperation(entity.getOperation())) {
                case "CREDIT":
                    handleCredit(transactionRequest, entity);
                    break;
                case "DEBIT":
                case "BUY":
                    handleDebitOrBuy(transactionRequest, entity);
                    break;
                case "CANCELBUY":
                    handleCancelBuy(entity);
                    return Mapper.map(entity, TransactionResponse.class);
                case "REFUND":
                    handleRefund(entity);
                    return Mapper.map(entity, TransactionResponse.class);
                default:
                    throw new IllegalArgumentException("Invalid operation type");
            }

            var response = transactionRepository.save(entity);
            return Mapper.map(response, TransactionResponse.class);
        } catch (Exception e) {
            throw new CustomValidatorException(e.getMessage());
        }
    }

    private void handleCredit(TransactionRequest transactionRequest, TransactionEntity entity) {
        var financialValueCredit = new FinancialValue();
        financialValueCredit.setAmount(transactionRequest.getValue());

        walletConsumer.deposit(Integer.parseInt(transactionRequest.getAccount().toString()), financialValueCredit);
        entity.setStatus(TransactionRequest.StatusTransactionEnum.LAN);
    }

    private void handleDebitOrBuy(TransactionRequest transactionRequest, TransactionEntity entity) {
        var financialValue = new FinancialValue();
        financialValue.setAmount(transactionRequest.getValue());

        if (!isSufficientBalance(transactionRequest)) {
            throw new RuntimeException("Insufficient balance");
        }

        walletConsumer.withdraw(Integer.parseInt(transactionRequest.getAccount().toString()), financialValue);
        entity.setStatus(TransactionRequest.StatusTransactionEnum.LAN);
    }

    private void handleCancelBuy(TransactionEntity entity) {
        entity = transactionRepository.findById(entity.getTransactionId()).orElseThrow(EntityNotFoundException::new);

        var financialValueCancelBuy = new FinancialValue();
        financialValueCancelBuy.setAmount(entity.getValue().doubleValue());

        walletConsumer.deposit(Integer.parseInt(entity.getAccount().toString()), financialValueCancelBuy);
        entity.setStatus(TransactionRequest.StatusTransactionEnum.CAN);
    }

    private void handleRefund(TransactionEntity entity) {
        entity = transactionRepository.findById(entity.getTransactionId()).orElseThrow(EntityNotFoundException::new);

        var financialValueRefund = new FinancialValue();
        financialValueRefund.setAmount(entity.getValue().doubleValue());

        walletConsumer.deposit(Integer.parseInt(entity.getAccount().toString()), financialValueRefund);
        entity.setStatus(TransactionRequest.StatusTransactionEnum.CAN);
    }

    private boolean isDuplicateTransaction(TransactionRequest transactionRequest) {
        return transactionRepository.existsByDateOperationAndAccountAndValueAndEstablishmentAndOperation(
                transactionRequest.getDateOperation(),
                transactionRequest.getAccount(),
                transactionRequest.getValue(),
                transactionRequest.getEstablishment(),
                transactionRequest.getOperation().intValue()
        );
    }

    @Override
    public TransactionResponse getTransactionById(Long transactionId) {
        var response = transactionRepository.findById(transactionId).orElseThrow(EntityNotFoundException::new);
        return Mapper.map(response, TransactionResponse.class);
    }

    @Override
    public List<TransactionResponse> listTransactions() {
        var response = transactionRepository.findAll();
        return response.stream().map(
                item -> Mapper.map(item, TransactionResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<TransactionResponse> listTransactionsByAccount(Long accountId) {
        var response = transactionRepository.findAllByAccount(accountId);
        return response.stream().map(
                item -> Mapper.map(item, TransactionResponse.class)).collect(Collectors.toList());
    }

    private Boolean isSufficientBalance(TransactionRequest transactionRequest) {
        var wallet = walletConsumer.getWallet(Integer.parseInt(transactionRequest.getAccount().toString()));
        return wallet.getAmount() >= transactionRequest.getValue();
    }

    private String getOperation(Integer operation) {
        return switch (operation) {
            case 1 -> "CREDIT";
            case 2 -> "DEBIT";
            case 3 -> "BUY";
            case 4 -> "CANCELBUY";
            case 5 -> "REFUND";
            default -> null;
        };
    }
}