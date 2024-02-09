package com.inditex.zarachallenge.domain.model;

import java.math.BigDecimal;
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
public class Offer {

  private Long id;

  private LocalDateTime validFrom;

  private BigDecimal price;

  private Long productId;

  private Product product;

}
