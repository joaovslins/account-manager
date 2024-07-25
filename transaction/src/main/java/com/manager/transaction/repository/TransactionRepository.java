package com.manager.transaction.repository;

import com.manager.transaction.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    List<TransactionEntity> findAllByAccount(Long account);

    boolean existsByDateOperationAndAccountAndValueAndEstablishmentAndOperation(
            LocalDate dateOperation, Long account, Double value, String establishment, Integer operation
    );
}
