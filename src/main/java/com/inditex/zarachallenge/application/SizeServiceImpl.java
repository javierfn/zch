package com.inditex.zarachallenge.application;

import java.sql.Timestamp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.inditex.zarachallenge.domain.model.Size;
import com.inditex.zarachallenge.domain.repository.SizeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class SizeServiceImpl implements SizeService {

	private final SizeRepository sizeRepository;

	@Override
	public Size updateAvailability(Long sizeId, Boolean availability, Timestamp update) {
		return sizeRepository.updateAvailability(sizeId, availability,update);
	}

}
