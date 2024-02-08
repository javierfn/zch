package com.inditex.zarachallenge.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.inditex.zarachallenge.domain.repository.SimilarRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class SimilarServiceImpl implements SimilarService {

	private final SimilarRepository similarRepository;


}
