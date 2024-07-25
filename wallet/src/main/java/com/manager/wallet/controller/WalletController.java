package com.manager.wallet.controller;

import com.manager.wallet.api.V1Api;
import com.manager.wallet.dto.CreateWalletRequest;
import com.manager.wallet.dto.CreateWalletResponse;
import com.manager.wallet.dto.FinancialValue;
import com.manager.wallet.service.IWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin
public class WalletController implements V1Api {

    private final IWalletService iWalletService;

    @Override
    public ResponseEntity<CreateWalletResponse> changeWalletStatus(Integer accountId) {
        var response = iWalletService.changeWalletStatus(accountId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CreateWalletResponse> createWallet(CreateWalletRequest createWalletRequest) {
        var response = iWalletService.createWallet(createWalletRequest);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CreateWalletResponse> deposit(Integer accountId, FinancialValue financialValue) {
        var response = iWalletService.deposit(accountId, financialValue);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CreateWalletResponse> getWallet(Integer accountId) {
        var response = iWalletService.getWallet(accountId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<CreateWalletResponse>> getWallets() {
        var response = iWalletService.getWallets();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CreateWalletResponse> withdraw(Integer accountId, FinancialValue financialValue) {
        var response = iWalletService.withdraw(accountId, financialValue);
        return ResponseEntity.ok(response);
    }
}