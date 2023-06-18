package com.ricardocreates.domain.entities.exception;

import com.ricardocreates.domain.entities.generic.ErrorResponse;

import java.util.Map;

public class AviationNotFoundException extends AviationBaseException {
    private static final int DEFAULT_HTTP_CODE = 404;

    public AviationNotFoundException(String message) {
        super(message, DEFAULT_HTTP_CODE);
    }

    public AviationNotFoundException(String message, Map<String, ErrorResponse> errors) {
        super(message, DEFAULT_HTTP_CODE, errors);
    }
}