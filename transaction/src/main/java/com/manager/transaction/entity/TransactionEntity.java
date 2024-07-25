package com.manager.transaction.entity;

import com.manager.transaction.dto.TransactionRequest;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long transactionId;
    private BigDecimal value;

    private Integer operation;

    private Long account;
    private String establishment;
    private LocalDate dateOperation;

    @Enumerated(EnumType.STRING)
    private TransactionRequest.StatusTransactionEnum status;
}
