package com.inditex.zarachallenge.application;

import java.sql.Timestamp;
import com.inditex.zarachallenge.domain.model.Size;

public interface SizeService {

  Size updateAvailability(Long sizeId, Boolean availability, Timestamp update);

}
