package com.inditex.zarachallenge.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import com.inditex.zarachallenge.domain.exception.OfferNotFoundException;
import com.inditex.zarachallenge.domain.exception.ProductNotFoundException;
import com.inditex.zarachallenge.domain.exception.SimilarProductsConnectionErrorException;
import com.inditex.zarachallenge.domain.exception.SimilarProductsErrorException;
import com.inditex.zarachallenge.domain.exception.SimilarProductsNotFoundException;
import com.inditex.zarachallenge.domain.exception.SizeNotFoundException;
import com.inditex.zarachallenge.infrastructure.dto.ErrorDTO;
import com.inditex.zarachallenge.shared.utils.ErrorUtils;
import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@RestControllerAdvice
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomGlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static ResponseEntity<ErrorDTO> handleConstraintViolationException(
        ConstraintViolationException exception, WebRequest request) {

        return ErrorUtils.createErrorInfoDtoResponseEntity(
            HttpStatus.BAD_REQUEST, exception, request, false);
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static ResponseEntity<ErrorDTO> handleNumberFormatException(
        NumberFormatException exception, WebRequest request) {

        return ErrorUtils.createErrorInfoDtoResponseEntity(
            HttpStatus.BAD_REQUEST, exception, request, false);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static ResponseEntity<ErrorDTO> handleProductNotFoundException(
        ProductNotFoundException exception, WebRequest request) {

        return ErrorUtils.createErrorInfoDtoResponseEntity(
            HttpStatus.NOT_FOUND, exception, request, true);
    }

    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static ResponseEntity<ErrorDTO> handleOfferNotFoundException(
        OfferNotFoundException exception, WebRequest request) {

        return ErrorUtils.createErrorInfoDtoResponseEntity(
            HttpStatus.NOT_FOUND, exception, request, true);
    }

    @ExceptionHandler(SizeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static ResponseEntity<ErrorDTO> handleSizeNotFoundException(
        SizeNotFoundException exception, WebRequest request) {

        return ErrorUtils.createErrorInfoDtoResponseEntity(
            HttpStatus.NOT_FOUND, exception, request, true);
    }

    @ExceptionHandler(SimilarProductsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static ResponseEntity<ErrorDTO> handleSimilarProductsNotFoundException(
        SimilarProductsNotFoundException exception, WebRequest request) {

        return ErrorUtils.createErrorInfoDtoResponseEntity(
            HttpStatus.NOT_FOUND, exception, request, true);
    }

    @ExceptionHandler(SimilarProductsConnectionErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static ResponseEntity<ErrorDTO> handleSimilarProductsConnectionErrorException(
        SimilarProductsConnectionErrorException exception, WebRequest request) {

        return ErrorUtils.createErrorInfoDtoResponseEntity(
            HttpStatus.INTERNAL_SERVER_ERROR, exception, request, true);
    }

    @ExceptionHandler(SimilarProductsErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static ResponseEntity<ErrorDTO> handleSimilarProductsErrorException(
        SimilarProductsErrorException exception, WebRequest request) {

        return ErrorUtils.createErrorInfoDtoResponseEntity(
            HttpStatus.INTERNAL_SERVER_ERROR, exception, request, true);
    }

}
