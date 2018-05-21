package com.N26.statistics.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnProcessableEntityException extends RuntimeException {

    public UnProcessableEntityException(String message) {
        super(message);
    }
}
