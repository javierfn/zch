package com.inditex.zarachallenge.infrastructure.dto;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {

  private Integer code;

  private String message;

  private OffsetDateTime timestamp;

  private String date;

  private String path;

}

