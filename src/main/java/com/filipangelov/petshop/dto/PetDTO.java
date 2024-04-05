package com.filipangelov.petshop.dto;

import com.filipangelov.petshop.domain.enums.PetType;
import java.time.LocalDate;

public record PetDTO(
    Long id,
    String name,
    PetType type,
    String description,
    LocalDate dateOfBirth,
    Integer rating,
    String owner
) {
}
