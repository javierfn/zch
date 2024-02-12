package com.inditex.zarachallenge.infrastructure.persistance.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OFFER")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OfferEntity implements Serializable {

  @Id
  @Column(name = "ID")
  @GeneratedValue(generator = "OFFER_ID_SEQ", strategy = GenerationType.SEQUENCE)
  @GenericGenerator(name = "OFFER_ID_SEQ",
      parameters = {
          @org.hibernate.annotations.Parameter(name = "sequence_name", value = "OFFER_ID_SEQ"),
          @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
          @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
      })
  private Long id;

  @Column(name = "VALID_FROM", nullable = false)
  private LocalDateTime validFrom;

  @Column(name = "PRICE")
  private BigDecimal price;

  @Column(name = "PRODUCT_ID", nullable = false)
  private Long productId;

  @ManyToOne(optional = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "PRODUCT_ID", nullable = false, insertable = false, updatable = false)
  private ProductEntity product;

}
