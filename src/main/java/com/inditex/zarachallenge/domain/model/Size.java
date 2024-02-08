package com.inditex.zarachallenge.domain.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Size {

  private Long sizeId;

  private String size;

  private Boolean availability;

  private LocalDateTime lastUpdated;

  private Long productId;

}