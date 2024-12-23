package com.thegaab.picpay.client;

import com.thegaab.picpay.client.dto.AuthorizationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "authorization-service",
        url = "${client.authorization-service.url}"
)
public interface AuthorizationClient {

    @GetMapping
    ResponseEntity<AuthorizationResponse> isAuthorized();
}
