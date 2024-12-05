package com.thegaab.picpay.service;

import com.thegaab.picpay.client.AuthorizationClient;
import com.thegaab.picpay.client.dto.TransferDto;
import com.thegaab.picpay.exception.PicPayException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final AuthorizationClient authorizationClient;

    public AuthorizationService(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    public boolean isAuthorized(@Valid TransferDto transfer) {
        var resp = authorizationClient.isAuthorized();

        if(resp.getStatusCode().isError()){
            throw new PicPayException();
        }

        return resp.getBody().authorized();
    }
}
