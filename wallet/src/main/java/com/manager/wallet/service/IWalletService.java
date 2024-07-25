package com.manager.wallet.service;

import com.manager.wallet.dto.CreateWalletRequest;
import com.manager.wallet.dto.CreateWalletResponse;
import com.manager.wallet.dto.FinancialValue;
import com.manager.wallet.exception.CustomValidatorException;

import java.util.List;

public interface IWalletService {

    CreateWalletResponse changeWalletStatus(Integer accountId);
    CreateWalletResponse createWallet(CreateWalletRequest createWalletRequest);
    CreateWalletResponse deposit(Integer accountId, FinancialValue financialValue);
    CreateWalletResponse getWallet(Integer accountId);
    List<CreateWalletResponse> getWallets();
    CreateWalletResponse withdraw(Integer accountId, FinancialValue financialValue);
}
