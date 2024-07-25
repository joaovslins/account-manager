package com.manager.wallet.service.impl;

import com.manager.wallet.dto.CreateWalletRequest;
import com.manager.wallet.dto.CreateWalletResponse;
import com.manager.wallet.dto.FinancialValue;
import com.manager.wallet.entity.WalletEntity;
import com.manager.wallet.exception.CustomValidatorException;
import com.manager.wallet.mapper.Mapper;
import com.manager.wallet.repository.WalletRepository;
import com.manager.wallet.service.IWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements IWalletService {

    private final WalletRepository repository;
    @Override
    public CreateWalletResponse changeWalletStatus(Integer accountId) {
        var account = repository.findById(accountId.longValue()).orElse(null);
        account.setStatus(account.getStatus().equals("BLOCKED") ? "UNBLOCKED" : "BLOCKED");
        repository.save(account);
        return Mapper.map(account, CreateWalletResponse.class);
    }

    @Override
    public CreateWalletResponse createWallet(CreateWalletRequest createWalletRequest){
            var entity = Mapper.map(createWalletRequest, WalletEntity.class);
            entity.setStatus("BLOCKED");
            entity.setAmount(0.0);

            entity = repository.save(entity);

            return Mapper.map(entity, CreateWalletResponse.class);
    }

    @Override
    public CreateWalletResponse deposit(Integer accountId, FinancialValue financialValue) {
        var account = repository.findById(accountId.longValue()).orElse(null);
        account.setAmount(account.getAmount() + financialValue.getAmount());
        repository.save(account);
        return Mapper.map(account, CreateWalletResponse.class);    }

    @Override
    public CreateWalletResponse getWallet(Integer accountId) {
        var entity = repository.findById(accountId.longValue()).orElse(null);
        return Mapper.map(entity, CreateWalletResponse.class);
    }

    @Override
    public List<CreateWalletResponse> getWallets() {
        var entities = repository.findAll();

        List<CreateWalletResponse> response =  new ArrayList<>();

        for (var entity : entities) {
            response.add(Mapper.map(entity, CreateWalletResponse.class));
        }

        return response;
    }

    @Override
    public CreateWalletResponse withdraw(Integer accountId, FinancialValue financialValue) {
        try {
            var account = repository.findById(accountId.longValue()).orElse(null);

            if(account.getAmount() < financialValue.getAmount()){
                throw new Exception("Insufficient funds.");
            }

            account.setAmount(account.getAmount() - financialValue.getAmount());
            repository.save(account);
            return Mapper.map(account, CreateWalletResponse.class);
        }catch (Exception e){
            try {
                throw new CustomValidatorException(e.getMessage());
            } catch (CustomValidatorException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}