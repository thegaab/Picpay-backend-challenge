package com.thegaab.picpay.controller;

import com.thegaab.picpay.dto.CreateWalletDTO;
import com.thegaab.picpay.entity.Wallet;
import com.thegaab.picpay.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/wallets")
    public ResponseEntity<Wallet> createWallet(@RequestBody @Valid CreateWalletDTO dto){

        var wallet = walletService.createWallet(dto);

        return ResponseEntity.ok(wallet);
    }
}
