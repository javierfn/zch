package com.inditex.zarachallenge.application;

import java.sql.Timestamp;
import java.util.Optional;
import com.inditex.zarachallenge.domain.model.Product;
import com.inditex.zarachallenge.domain.model.ProductDetail;
import com.inditex.zarachallenge.domain.model.Size;

public interface SizeService {

  Size updateAvailability(Long sizeId, Boolean availability, Timestamp update);

}
