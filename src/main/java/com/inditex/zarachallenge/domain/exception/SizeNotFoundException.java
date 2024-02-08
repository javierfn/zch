package com.inditex.zarachallenge.domain.exception;

public class SizeNotFoundException extends RuntimeException {

    public SizeNotFoundException() {
        super("size.not.found.exception");
    }

}
