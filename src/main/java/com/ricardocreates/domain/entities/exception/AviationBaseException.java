package com.ricardocreates.domain.entities.exception;

import com.ricardocreates.domain.entities.generic.ErrorResponse;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class AviationBaseException extends RuntimeException {
    protected int httpCode;
    protected Map<String, ErrorResponse> errors;

    public AviationBaseException(String message) {
        this(message, 500);
    }

    public AviationBaseException(String message, int httpCode) {
        this(message, httpCode, new HashMap<>());
    }

    public AviationBaseException(String message, Map<String, ErrorResponse> errors) {
        this(message, 500, errors);
    }

    public AviationBaseException(String message, int httpCode, Map<String, ErrorResponse> errors) {
        super(message);
        this.httpCode = httpCode;
        this.errors = errors;
    }


}
