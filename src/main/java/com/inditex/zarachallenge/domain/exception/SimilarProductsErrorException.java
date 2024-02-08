package com.inditex.zarachallenge.domain.exception;

public class SimilarProductsErrorException extends RuntimeException {

    public SimilarProductsErrorException() {
        super("similar.products.error.exception");
    }

}
