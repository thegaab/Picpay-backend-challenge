package com.thegaab.picpay.config;

import com.thegaab.picpay.entity.WalletType;
import com.thegaab.picpay.repository.WalletTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final WalletTypeRepository walletTypeRepository;

    public DataLoader(WalletTypeRepository walletTypeRepository) {
        this.walletTypeRepository = walletTypeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (walletTypeRepository.count() == 0) {
            Arrays.stream(WalletType.Enum.values())
                    .map(WalletType.Enum::get)
                    .forEach(walletTypeRepository::save);
        }
    }
}
