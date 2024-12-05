package com.thegaab.picpay.client.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferDto(@DecimalMin("0.01") @NotNull BigDecimal valuer,
                          @NotNull Long payer,
                          @NotNull  Long payee) {
}
