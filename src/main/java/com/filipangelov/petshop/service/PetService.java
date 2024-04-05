package com.filipangelov.petshop.service;

import com.filipangelov.petshop.domain.Dog;
import com.filipangelov.petshop.domain.Pet;
import com.filipangelov.petshop.domain.User;
import com.filipangelov.petshop.domain.enums.PetType;
import com.filipangelov.petshop.dto.PetDTO;
import com.filipangelov.petshop.repository.PetRepository;
import com.filipangelov.petshop.service.factory.PetFactory;
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
    private final HistoryLogService historyLogService;

    /**
     * This is a greedy approach of buying pets to all users which budget is bigger than 0 and also the pets should not
     * have an owner. This method is also writing in the HistoryLog.*/
    public User buyPet(User user) {
        log.info("Buying a pet for user " + user.getFullName());
        List<Pet> availablePets = petRepository.findAllByOwnerIsNull();
        availablePets.forEach(pet -> {
            if (pet.price() <= user.getBudget()) {
                LocalDate today = LocalDate.now();
                pet.setOwner(user);
                pet.setDateOfOwnerShip(today);
                user.setBudget(user.getBudget() - pet.price());
                petFactory.updatePet(pet);
                log.info(pet.aPetIsBought());
                historyLogService.userBoughtPet(today);
            }
        });
        return user;
    }

    public List<PetDTO> createRandomPets() {
        log.info("Creating random pets");
        Random random = new Random();
        return IntStream.range(0, random.nextInt(1, 21))
            .mapToObj(d -> {
                var petNumber = random.nextInt(1, 1001);
                PetType petType = PetType.getPetType(random.nextInt(0, 2));
                return petFactory.createPet(
                    petType,
                    "Name" + petNumber,
                    "Description " + petNumber,
                    LocalDate.now().minusYears(random.nextInt(1, 10)));
            }).map(this::mapToDTO).toList();
    }

    public List<PetDTO> findAll() {
        return petRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public List<PetDTO> mapPetsDTO(List<Pet> pets) {
        return pets.stream().map(this::mapToDTO).toList();
    }

    private PetDTO mapToDTO(Pet pet) {
        PetType petType = PetType.valueOf(pet.petType());
        String petOwner = Objects.nonNull(pet.getOwner()) ? pet.getOwner().getFullName() : null;
        return switch (petType) {
            case DOG -> new PetDTO(pet.getId(), pet.getName(), petType, pet.getDescription(), pet.getDateOfBirth(),
                ((Dog) pet).getRating(), petOwner);
            case CAT ->
                new PetDTO(pet.getId(), pet.getName(), petType, pet.getDescription(), pet.getDateOfBirth(), null,
                    petOwner);
        };
    }

    public List<Long> usersBoughtAPetOnDate(LocalDate date) {
        return petRepository.findAllByDateOfOwnerShip(date).stream().map(it -> it.getOwner().getId()).toList();
    }
}
