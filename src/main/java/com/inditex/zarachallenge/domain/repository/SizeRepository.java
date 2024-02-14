package com.inditex.zarachallenge.domain.repository;

import java.sql.Timestamp;
import java.util.List;
import com.inditex.zarachallenge.domain.model.Size;


public interface SizeRepository {

  List<Size> findSizeListByProductId(final Long productId);

  Size updateAvailability(Long sizeId, Boolean availability, Timestamp update);

}
