package com.filipangelov.petshop.service;

import com.filipangelov.petshop.domain.User;
import com.filipangelov.petshop.dto.UserDTO;
import com.filipangelov.petshop.exceptions.UserNotFoundException;
import com.filipangelov.petshop.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PetService petService;

    public UserDTO createOrUpdateUser(UserDTO userDTO) {
        return Optional.ofNullable(userDTO.id())
            .map(userId -> mapToDTO(userRepository.findById(userDTO.id()).map(u -> {
                u.setFirstName(userDTO.firstName());
                u.setLastName(userDTO.lastName());
                u.setEmailAddress(userDTO.emailAddress());
                u.setBudget(userDTO.budget());
                return userRepository.save(u);
            }).orElseThrow(() -> new UserNotFoundException(userDTO.firstName(), userDTO.lastName()))))
            .orElseGet(() -> mapToDTO(userRepository.save(User.builder()
                .firstName(userDTO.firstName())
                .lastName(userDTO.lastName())
                .emailAddress(userDTO.emailAddress())
                .budget(userDTO.budget())
                .build())));
    }

    private UserDTO mapToDTO(User user) {
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmailAddress(),
            user.getBudget(), Optional.ofNullable(user.getPets()).map(petService::mapPetsDTO).orElse(List.of()));
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public List<UserDTO> createRandomUsers() {
        Random oneRandom = new Random();
        return userRepository.saveAll(
                IntStream.range(0, oneRandom.nextInt(1, 11))
                    .mapToObj(d -> {
                        var userNumber = oneRandom.nextInt(1, 1001);
                        return User.builder()
                            .firstName("First" + userNumber)
                            .lastName("Last" + userNumber)
                            .emailAddress("first.last" + userNumber + "@mail.com")
                            .budget(oneRandom.nextInt(1,16))
                            .build();
                    })
                    .toList())
            .stream()
            .map(this::mapToDTO)
            .toList();
    }

    public void buyPets() {
        userRepository.findAllByBudget().forEach(user -> userRepository.save(petService.buyPet(user)));
    }

    public void buyPet() {
        // todo: user must have enough budget to buy a pet
        // todo: cannot buy a pet with owner
    }
}
