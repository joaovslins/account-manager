package com.manager.transaction.service;

import com.manager.transaction.consumer.WalletConsumer;
import com.manager.transaction.consumer.dto.FinancialValue;
import com.manager.transaction.dto.TransactionRequest;
import com.manager.transaction.dto.TransactionResponse;
import com.manager.transaction.entity.TransactionEntity;
import com.manager.transaction.repository.TransactionRepository;
import com.manager.transaction.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private WalletConsumer walletConsumer;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private TransactionRequest request;
    private TransactionEntity entity;
    private FinancialValue financialValue;

    @BeforeEach
    void setUp() {
        request = new TransactionRequest();
        request.setValue(100.0);
        request.setOperation(1L);
        request.setAccount(123L);
        request.setEstablishment("Store");
        request.setDateOperation(LocalDate.now());

        entity = new TransactionEntity();
        entity.setTransactionId(1L);
        entity.setValue(BigDecimal.valueOf(request.getValue()));
        entity.setOperation(request.getOperation().intValue());
        entity.setAccount(request.getAccount());
        entity.setEstablishment(request.getEstablishment());
        entity.setDateOperation(LocalDate.now());

        financialValue = new FinancialValue();
        financialValue.setAmount(request.getValue());
    }


    @Test
    void testGetTransactionById_Success() {

        when(transactionRepository.findById(eq(1L))).thenReturn(Optional.of(entity));

        TransactionResponse response = transactionService.getTransactionById(1L);

        assertNotNull(response);
        assertEquals(entity.getTransactionId(), response.getTransactionId());
    }

    @Test
    void testListTransactions_Success() {

        when(transactionRepository.findAll()).thenReturn(List.of(entity));

        List<TransactionResponse> responses = transactionService.listTransactions();

        assertNotNull(responses);
        assertEquals(1, responses.size());
    }

    @Test
    void testListTransactionsByAccount_Success() {

        when(transactionRepository.findAllByAccount(eq(123L))).thenReturn(List.of(entity));

        List<TransactionResponse> responses = transactionService.listTransactionsByAccount(123L);

        assertNotNull(responses);
        assertEquals(1, responses.size());
    }
}
