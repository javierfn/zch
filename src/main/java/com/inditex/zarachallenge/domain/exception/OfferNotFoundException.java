package com.inditex.zarachallenge.domain.exception;

public class OfferNotFoundException extends RuntimeException {

    public OfferNotFoundException() {
        super("offer.not.found.exception");
    }

}
