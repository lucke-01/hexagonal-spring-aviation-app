package com.ricardocreates.domain.entities.generic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swagger.client.codegen.rest.model.ErrorResponseDto;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class GenericErrorResponse {
    private String code;
    private String message;

    @Builder.Default
    private List<ErrorResponse> errors = new ArrayList<>();
}
