package com.filipangelov.petshop.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String firstName, String lastName) {
        super(String.format("User %s %s was not found", firstName, lastName));
    }
}
