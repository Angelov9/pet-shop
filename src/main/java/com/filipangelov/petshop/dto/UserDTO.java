package com.filipangelov.petshop.dto;

import java.util.List;

public record UserDTO(Long id, String firstName, String lastName, String emailAddress, int budget, List<PetDTO> pets) {
}
