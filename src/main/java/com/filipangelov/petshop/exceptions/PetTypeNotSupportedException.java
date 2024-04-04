package com.filipangelov.petshop.exceptions;

public class PetTypeNotSupportedException extends RuntimeException {

    public PetTypeNotSupportedException(String type) {
        super(String.format("Pet [%s] type is not supported!", type));
    }
}
