package com.inditex.zarachallenge.domain.model;

import java.math.BigDecimal;
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
public class ProductDetail {

  private Long id;

  private String name;

  private BigDecimal price;

  private Boolean availability;

}

