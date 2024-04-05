package com.filipangelov.petshop.api;

import com.filipangelov.petshop.dto.PetDTO;
import com.filipangelov.petshop.service.PetService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    @GetMapping("/create-pets")
    public List<PetDTO> createRandomPets() {
        return petService.createRandomPets();
    }

    @GetMapping
    public List<PetDTO> getAllPets() {
        return petService.findAll();
    }
}
