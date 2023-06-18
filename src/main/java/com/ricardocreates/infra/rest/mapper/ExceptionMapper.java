package com.ricardocreates.infra.rest.mapper;

import com.ricardocreates.domain.entities.exception.AviationBaseException;
import com.ricardocreates.domain.entities.generic.ErrorResponse;
import com.swagger.client.codegen.rest.model.ErrorResponseDto;
import com.swagger.client.codegen.rest.model.GenericErrorResponseDto;
import org.mapstruct.Mapper;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface ExceptionMapper {
    default ResponseEntity<GenericErrorResponseDto> baseExceptionToResponseEntity(AviationBaseException aviationBaseException) {
        GenericErrorResponseDto errorResponseDto = new GenericErrorResponseDto();
        errorResponseDto.setCode(String.valueOf(aviationBaseException.getHttpCode()));
        errorResponseDto.setMessage(aviationBaseException.getMessage());

        errorResponseDto.setErrors(errorResponseMapFromDomain(aviationBaseException.getErrors()));

        ResponseEntity<GenericErrorResponseDto> response = ResponseEntity
                .status(aviationBaseException.getHttpCode())
                .body(errorResponseDto);

        return response;
    }

    ErrorResponseDto errorResponseFromDomain(ErrorResponse errorResponse);

    Map<String, ErrorResponseDto> errorResponseMapFromDomain(Map<String, ErrorResponse> errors);
}
