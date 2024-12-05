package com.thegaab.picpay.service;

import com.thegaab.picpay.client.dto.TransferDto;
import com.thegaab.picpay.entity.Transfer;
import com.thegaab.picpay.entity.Wallet;
import com.thegaab.picpay.exception.*;
import com.thegaab.picpay.repository.TransferRepository;
import com.thegaab.picpay.repository.WalletRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;
    private final WalletRepository walletRepository;

    public TransferService(TransferRepository transferRepository,
                           AuthorizationService authorizationService,
                           NotificationService notificationService,
                           WalletRepository walletRepository) {
        this.transferRepository = transferRepository;
        this.authorizationService = authorizationService;
        this.notificationService = notificationService;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Transfer transfer(@Valid TransferDto transferDto) {
        var sender = walletRepository.findById(transferDto.payer())
                .orElseThrow(() -> new WalletNotFoundException(transferDto.payer()));

        var receiver = walletRepository.findById(transferDto.payee())
                .orElseThrow(() -> new WalletNotFoundException(transferDto.payee()));

        validateTransfer(transferDto, sender);

        sender.debit(transferDto.valuer());
        receiver.credit(transferDto.valuer());

        var transfer = new Transfer(sender, receiver, transferDto.valuer());

        walletRepository.save(sender);
        walletRepository.save(receiver);
        var transferResult = transferRepository.save(transfer);

        CompletableFuture.runAsync(() -> notificationService.sendNotification(transferResult));

        return transferResult;
    }

    private void validateTransfer(@Valid TransferDto transferDto, Wallet sender) {

        if(!sender.isTransferAllowedForWalletType){
            throw new TransferNotAllowedForWalletTypeException();
        }

        if (!sender.isBalancerEqualOrGreatherThan(transferDto.valuer())) {
            throw new InsufficientBalanceException();
        }

        if (!authorizationService.isAuthorized(transferDto)) {
            throw new TransferNotAuthorizedException();
        }
    }
}
