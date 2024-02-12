package com.inditex.zarachallenge.infrastructure.persistance.entity;

import java.io.Serializable;
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
@Table(name = "\"SIZE\"")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SizeEntity implements Serializable {

  @Id
  @Column(name = "SIZE_ID")
  @GeneratedValue(generator = "SIZE_ID_SEQ", strategy = GenerationType.SEQUENCE)
  @GenericGenerator(name = "SIZE_ID_SEQ",
      parameters = {
          @org.hibernate.annotations.Parameter(name = "sequence_name", value = "SIZE_ID_SEQ"),
          @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
          @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
      })
  private Long sizeId;

  @Column(name = "\"SIZE\"", length = 5, nullable = false)
  private String size;

  @Column(name = "AVAILABILITY")
  private Boolean availability;

  @Column(name = "LAST_UPDATED")
  private LocalDateTime lastUpdated;

  @Column(name = "PRODUCT_ID", nullable = false)
  private Long productId;

  @ManyToOne(optional = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "PRODUCT_ID", nullable = false, insertable = false, updatable = false)
  private ProductEntity product;

}
