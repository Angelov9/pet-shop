package com.filipangelov.petshop.service;

import com.filipangelov.petshop.domain.Dog;
import com.filipangelov.petshop.domain.Pet;
import com.filipangelov.petshop.domain.User;
import com.filipangelov.petshop.domain.enums.PetType;
import com.filipangelov.petshop.domain.factory.PetFactory;
import com.filipangelov.petshop.dto.PetDTO;
import com.filipangelov.petshop.exceptions.PetTypeNotSupportedException;
import com.filipangelov.petshop.repository.PetRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PetService {

    private final PetRepository petRepository;
    private final PetFactory petFactory;

    public User buyPet(User user) {
        List<Pet> availablePets = petRepository.findAllByOwnerIsNull();
        availablePets.forEach(pet -> {
            if (pet.price() <= user.getBudget()) {
                pet.setOwner(user);
                user.setBudget(user.getBudget() - pet.price());
                petFactory.updatePet(pet);
                log.info(pet.aPetIsBought());;
            }
        });
        return user;
    }

    public List<PetDTO> createRandomPets() {
        Random oneRandom = new Random();
        return IntStream.range(0, oneRandom.nextInt(1, 21))
            .mapToObj(d -> {
                var petNumber = oneRandom.nextInt(1001);
                PetType petType = PetType.getPetType(oneRandom.nextInt(0, 2));
                return petFactory.createPet(
                    petType,
                    "Name" + petNumber,
                    "Description " + petNumber,
                    LocalDate.now().minusYears(oneRandom.nextInt(1,10)));
            }).map(this::mapToDTO).toList();
    }

    private PetDTO mapToDTO(Pet pet) {
        PetType petType = PetType.valueOf(pet.petType());
        String petOwner = Objects.nonNull(pet.getOwner()) ? pet.getOwner().getFullName() : null;
        return switch (petType) {
            case DOG ->
                new PetDTO(pet.getId(), pet.getName(), petType, pet.getDescription(), pet.getDateOfBirth(), ((Dog) pet).getRating(), petOwner);
            case CAT ->
                new PetDTO(pet.getId(), pet.getName(), petType, pet.getDescription(), pet.getDateOfBirth(), null, petOwner);
            default -> throw new PetTypeNotSupportedException(pet.petType());
        };
    }

    public List<PetDTO> findAll() {
        return petRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public List<PetDTO> mapPetsDTO(List<Pet> pets) {
        return pets.stream().map(this::mapToDTO).toList();
    }
}
