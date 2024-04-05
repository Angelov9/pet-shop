package com.filipangelov.petshop.service.factory;

import com.filipangelov.petshop.domain.Cat;
import com.filipangelov.petshop.domain.Dog;
import com.filipangelov.petshop.domain.Pet;
import com.filipangelov.petshop.domain.enums.PetType;
import com.filipangelov.petshop.exceptions.PetTypeNotSupportedException;
import com.filipangelov.petshop.repository.CatRepository;
import com.filipangelov.petshop.repository.DogRepository;
import java.time.LocalDate;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PetFactory {

    private final CatRepository catRepository;
    private final DogRepository dogRepository;

    public Pet createPet(PetType type, String name, String description, LocalDate dateOfBirth) {
        switch (type) {
            case CAT -> {
                return catRepository.save(
                    Cat.builder()
                        .name(name)
                        .description(description)
                        .dateOfBirth(dateOfBirth)
                        .build());
            }
            case DOG -> {
                Random rating = new Random();
                return dogRepository.save(
                    Dog.builder()
                        .name(name)
                        .description(description)
                        .dateOfBirth(dateOfBirth)
                        .rating(rating.nextInt(1, 11))
                        .build());
            }
            default -> throw new PetTypeNotSupportedException(type.name());
        }
    }

    public Pet updatePet(Pet pet) {
        switch (PetType.valueOf(pet.petType())) {
            case CAT -> {
                return catRepository.save((Cat) pet);
            }
            case DOG -> {
                return dogRepository.save((Dog) pet);
            }
            default -> throw new PetTypeNotSupportedException(pet.petType());
        }
    }
}
