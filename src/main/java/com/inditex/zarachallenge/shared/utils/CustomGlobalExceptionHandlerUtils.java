package com.inditex.zarachallenge.shared.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.inditex.zarachallenge.infrastructure.dto.ErrorDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomGlobalExceptionHandlerUtils {

    public static ResponseEntity<ErrorDTO> createErrorInfoDtoResponseEntity(ErrorDTO errorDTO) {
        return new ResponseEntity<>(errorDTO, HttpStatus.valueOf(errorDTO.getCode()));
    }

}
