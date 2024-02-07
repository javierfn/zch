package com.inditex.zarachallenge.infrastructure.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Product detail
 */
@lombok.Builder(toBuilder = true)
@lombok.With

@Schema(name = "ProductDetail", description = "Product detail")
@JsonTypeName("ProductDetail")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ProductDetailDTO {

  private String id;

  private String name;

  private BigDecimal price;

  private Boolean availability;

  public ProductDetailDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ProductDetailDTO(String id, String name, BigDecimal price, Boolean availability) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.availability = availability;
  }

  public ProductDetailDTO id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @NotNull @Size(min = 1) 
  @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ProductDetailDTO name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  @NotNull @Size(min = 1) 
  @Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ProductDetailDTO price(BigDecimal price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * @return price
  */
  @NotNull @Valid 
  @Schema(name = "price", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("price")
  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public ProductDetailDTO availability(Boolean availability) {
    this.availability = availability;
    return this;
  }

  /**
   * Get availability
   * @return availability
  */
  @NotNull 
  @Schema(name = "availability", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("availability")
  public Boolean getAvailability() {
    return availability;
  }

  public void setAvailability(Boolean availability) {
    this.availability = availability;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductDetailDTO productDetail = (ProductDetailDTO) o;
    return Objects.equals(this.id, productDetail.id) &&
        Objects.equals(this.name, productDetail.name) &&
        Objects.equals(this.price, productDetail.price) &&
        Objects.equals(this.availability, productDetail.availability);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, price, availability);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductDetailDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    availability: ").append(toIndentedString(availability)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

