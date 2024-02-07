package com.inditex.zarachallenge.infrastructure.persistance.entity;

import java.io.Serializable;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductEntity implements Serializable {

  @Id
  @Column(name = "ID")
  @GeneratedValue(generator = "PRODUCT_ID_SEQ", strategy = GenerationType.SEQUENCE)
  @GenericGenerator(name = "PRODUCT_ID_SEQ",
      parameters = {
          @org.hibernate.annotations.Parameter(name = "sequence_name", value = "PRODUCT_ID_SEQ"),
          @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
          @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
      })
  private Long id;

  @Column(name = "NAME", length = 100, nullable = false)
  private String name;

}
