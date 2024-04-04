package com.filipangelov.petshop.domain.enums;

import java.util.Arrays;

public enum PetType {
    CAT,
    DOG;

    public static PetType getPetType(int id) {
        return Arrays.stream(PetType.values()).filter(it -> it.ordinal() == id).findFirst().orElse(null);
    }
}
