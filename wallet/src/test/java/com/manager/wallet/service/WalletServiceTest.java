package com.manager.wallet.service;

import com.manager.wallet.dto.CreateWalletRequest;
import com.manager.wallet.dto.CreateWalletResponse;
import com.manager.wallet.dto.FinancialValue;
import com.manager.wallet.entity.WalletEntity;
import com.manager.wallet.exception.CustomValidatorException;
import com.manager.wallet.repository.WalletRepository;
import com.manager.wallet.service.impl.WalletServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class WalletServiceTest {


    @Mock
    private WalletRepository repository;

    @InjectMocks
    private WalletServiceImpl walletService;



    @Test
    void changeWalletStatus() {
        WalletEntity account = new WalletEntity();
        account.setStatus("BLOCKED");

        when(repository.findById(1L)).thenReturn(Optional.of(account));
        when(repository.save(any(WalletEntity.class))).thenAnswer(invocation -> {
            WalletEntity saved = invocation.getArgument(0);
            saved.setStatus("UNBLOCKED");
            return saved;
        });

        CreateWalletResponse response = walletService.changeWalletStatus(1);

        assertNotNull(response);
        assertEquals("UNBLOCKED", response.getStatus());
    }

    @Test
    void createWallet() {
        CreateWalletRequest request = new CreateWalletRequest();
        WalletEntity entity = new WalletEntity();
        request.setOwner("joao");
        request.setDigit(1L);
        request.setNumber(123L);
        entity.setStatus("BLOCKED");

        when(repository.save(any(WalletEntity.class))).thenReturn(entity);
        CreateWalletResponse response = walletService.createWallet(request);

        assertNotNull(response);
        assertEquals("BLOCKED", response.getStatus());
    }

    @Test
    void deposit() {
        WalletEntity account = new WalletEntity();
        account.setAmount(100.0);

        FinancialValue financialValue = new FinancialValue();
        financialValue.setAmount(50.0);

        when(repository.findById(1L)).thenReturn(Optional.of(account));
        when(repository.save(any(WalletEntity.class))).thenAnswer(invocation -> {
            WalletEntity saved = invocation.getArgument(0);
            saved.setAmount(saved.getAmount() + financialValue.getAmount());
            return saved;
        });

        CreateWalletResponse response = walletService.deposit(1, financialValue);

        assertNotNull(response);
        assertEquals(200.0, response.getAmount());
    }

    @Test
    void getWallet() {
        WalletEntity entity = new WalletEntity();
        entity.setAmount(100.0);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        CreateWalletResponse response = walletService.getWallet(1);

        assertNotNull(response);
        assertEquals(100.0, response.getAmount());
    }

    @Test
    void getWallets() {
        WalletEntity entity = new WalletEntity();
        entity.setAmount(100.0);
        List<WalletEntity> entities = List.of(entity);

        when(repository.findAll()).thenReturn(entities);
        List<CreateWalletResponse> responses = walletService.getWallets();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(100.0, responses.get(0).getAmount());
    }

    @Test
    void withdraw() {
        WalletEntity account = new WalletEntity();
        account.setAmount(100.0);

        FinancialValue financialValue = new FinancialValue();
        financialValue.setAmount(50.0);

        when(repository.findById(1L)).thenReturn(Optional.of(account));
        when(repository.save(any(WalletEntity.class))).thenAnswer(invocation -> {
            WalletEntity saved = invocation.getArgument(0);
            saved.setAmount(saved.getAmount() - financialValue.getAmount());
            return saved;
        });

        CreateWalletResponse response = walletService.withdraw(1, financialValue);

        assertNotNull(response);
        assertEquals(0.0, response.getAmount());
    }

    @Test
    void withdrawInsufficientFunds() {
        WalletEntity account = new WalletEntity();
        account.setAmount(30.0);

        FinancialValue financialValue = new FinancialValue();
        financialValue.setAmount(50.0);

        when(repository.findById(1L)).thenReturn(Optional.of(account));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            walletService.withdraw(1, financialValue);
        });

        assertEquals("com.manager.wallet.exception.CustomValidatorException: Insufficient funds.", exception.getMessage());
    }
}