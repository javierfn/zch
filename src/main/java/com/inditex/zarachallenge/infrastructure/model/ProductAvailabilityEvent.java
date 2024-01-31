package com.inditex.zarachallenge.infrastructure.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductAvailabilityEvent implements Serializable {

	private static final long serialVersionUID = -6915528229848073283L;

	private Long sizeId;

	private boolean availability;

	private Timestamp update;

}
