package com.inditex.zarachallenge.domain.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("product.not.found.exception");
    }

}
