package com.thegaab.picpay.service;

import com.thegaab.picpay.dto.CreateWalletDTO;
import com.thegaab.picpay.entity.Wallet;
import com.thegaab.picpay.exception.CustomerDataAlreadyExistisException;
import com.thegaab.picpay.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(CreateWalletDTO dto) {

        var walletDb = walletRepository.findByCpfCnpjOrEmail(dto.cpfCnpj(), dto.email());
        if (walletDb.isPresent()){
            throw new CustomerDataAlreadyExistisException("CPF/CNPJ or Email already exists");
        }

        return walletRepository.save(dto.toWallet());
    }
}
