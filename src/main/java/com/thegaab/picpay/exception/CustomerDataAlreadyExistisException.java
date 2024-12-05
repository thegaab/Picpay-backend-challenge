package com.thegaab.picpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class CustomerDataAlreadyExistisException extends PicPayException{

    private String detail;

    public CustomerDataAlreadyExistisException(String detail) {
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("Customer data already exists");
        pb.setDetail(detail);

        return pb;
    }
}
