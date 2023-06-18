package com.ricardocreates.domain.entities.exception;

import com.ricardocreates.domain.entities.generic.ErrorResponse;

import java.util.Map;

public class AviationServerException extends AviationBaseException {
    private static final int DEFAULT_HTTP_CODE = 500;

    public AviationServerException(String message) {
        super(message, DEFAULT_HTTP_CODE);
    }

    public AviationServerException(String message, Map<String, ErrorResponse> errors) {
        super(message, DEFAULT_HTTP_CODE, errors);
    }
}
