package com.ricardocreates.infra.rest;

import com.ricardocreates.domain.entities.exception.AviationBaseException;
import com.ricardocreates.infra.rest.mapper.ExceptionMapper;
import com.swagger.client.codegen.rest.model.ErrorResponseDto;
import com.swagger.client.codegen.rest.model.GenericErrorResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler {
    private final ExceptionMapper exceptionMapper;

    @ExceptionHandler(WebExchangeBindException.class)
    protected ResponseEntity<String> handleEntityServiceUnavailable(WebExchangeBindException webBindException) {
        StringBuilder errorsStringBuilder = new StringBuilder(100);
        for (int index = 0; index < webBindException.getBindingResult().getFieldErrors().size(); index++) {
            FieldError fieldError = webBindException.getBindingResult().getFieldErrors().get(index);
            errorsStringBuilder.append(String.format("%s(%s): %s", fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage()));
            if ((index + 1) < webBindException.getBindingResult().getFieldErrors().size()) {
                errorsStringBuilder.append("\n");
            }
        }
        String errorsString = errorsStringBuilder.isEmpty() ? "Bad request" : errorsStringBuilder.toString();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsString);
    }

    @ExceptionHandler(AviationBaseException.class)
    protected ResponseEntity<GenericErrorResponseDto> handleEntityServiceUnavailable(AviationBaseException aviationBaseException) {
        return exceptionMapper.baseExceptionToResponseEntity(aviationBaseException);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<GenericErrorResponseDto> handleEntityServiceUnavailable(MethodArgumentNotValidException exception) {
        GenericErrorResponseDto errorResponseDto = new GenericErrorResponseDto();
        errorResponseDto.setCode(HttpStatus.BAD_REQUEST.toString());
        errorResponseDto.setMessage("Bad request parameter");
        Map<String, ErrorResponseDto> errors = new HashMap<>();
        for (int index = 0; index < exception.getBindingResult().getFieldErrors().size(); index++) {
            FieldError fieldError = exception.getBindingResult().getFieldErrors().get(index);
            ErrorResponseDto errorResponse = new ErrorResponseDto();
            errorResponse.setDescription(fieldError.getCode());
            errorResponse.setMessage(fieldError.getDefaultMessage());
            errors.put(fieldError.getField(), errorResponse);
        }
        errorResponseDto.setErrors(errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    /**
     * this will be triggered when some method parameter is missing
     *
     * @param reqException reqException
     * @return response entity
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<GenericErrorResponseDto> handleEntityServiceUnavailable(MissingServletRequestParameterException reqException) {
        final GenericErrorResponseDto errorResponseDto = new GenericErrorResponseDto();
        errorResponseDto.setCode(HttpStatus.BAD_REQUEST.toString());
        errorResponseDto.setMessage(reqException.getMessage());

        Map<String, ErrorResponseDto> errors = new HashMap<>();
        ErrorResponseDto error = new ErrorResponseDto();
        error.setMessage("Missing parameter");
        error.setDescription(String.format("parameter type: %s", reqException.getParameterType()));
        errors.put(reqException.getParameterName(), error);
        errorResponseDto.setErrors(errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<GenericErrorResponseDto> handleEntityServiceUnavailable(MethodArgumentTypeMismatchException missMatch) {
        final GenericErrorResponseDto errorResponseDto = new GenericErrorResponseDto();
        errorResponseDto.setCode(HttpStatus.BAD_REQUEST.toString());
        errorResponseDto.setMessage(missMatch.getMessage());

        Map<String, ErrorResponseDto> errors = new HashMap<>();
        ErrorResponseDto error = new ErrorResponseDto();
        error.setMessage("Missing parameter");
        error.setDescription(String.format("parameter type: %s", missMatch.getParameter().getParameterType()));
        errors.put(missMatch.getParameter().getParameterName(), error);
        errorResponseDto.setErrors(errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }
}
