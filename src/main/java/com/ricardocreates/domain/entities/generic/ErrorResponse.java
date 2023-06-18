package com.ricardocreates.domain.entities.generic;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

/**
 * ErrorResponse
 */
@Data
public class ErrorResponse {
  private String description;
  private String message;
}

