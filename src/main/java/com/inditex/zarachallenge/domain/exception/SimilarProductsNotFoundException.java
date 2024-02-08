package com.inditex.zarachallenge.domain.exception;

public class SimilarProductsNotFoundException extends RuntimeException {

    public SimilarProductsNotFoundException() {
        super("similar.products.not.found.exception");
    }

}
