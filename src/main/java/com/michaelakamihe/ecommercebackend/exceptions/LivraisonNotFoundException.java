package com.michaelakamihe.ecommercebackend.exceptions;

public class LivraisonNotFoundException  extends RuntimeException {
    public LivraisonNotFoundException(String message) {
        super(message);
    }
}