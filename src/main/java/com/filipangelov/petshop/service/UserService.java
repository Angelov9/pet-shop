package com.filipangelov.petshop.service;

import com.filipangelov.petshop.domain.User;
import com.filipangelov.petshop.dto.UserDTO;
import com.filipangelov.petshop.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PetService petService;
    private final HistoryLogService historyLogService;

    public void buyPets() {
        log.info("Buying pets");
        LocalDate today = LocalDate.now();
        List<User> users = userRepository.findAllByBudget();
        userRepository.saveAll(users.stream().map(petService::buyPet).toList());

        List<Long> usersBoughtAPetToday = petService.usersBoughtAPetOnDate(today);

        historyLogService.userNotAllowedToBuyAnyPet(today,
            users.size() - users.stream()
                .map(User::getId)
                .filter(usersBoughtAPetToday::contains)
                .toList()
                .size());
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    public List<UserDTO> createRandomUsers() {
        log.info("Creating random users");
        Random random = new Random();
        return userRepository.saveAll(
                IntStream.range(0, random.nextInt(1, 11))
                    .mapToObj(d -> {
                        var userNumber = random.nextInt(1, 1001);
                        return User.builder()
                            .firstName("First" + userNumber)
                            .lastName("Last" + userNumber)
                            .emailAddress("first.last" + userNumber + "@mail.com")
                            .budget(random.nextInt(1, 16))
                            .build();
                    })
                    .toList())
            .stream()
            .map(this::mapToDTO)
            .toList();
    }

    private UserDTO mapToDTO(User user) {
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmailAddress(),
            user.getBudget(), Optional.ofNullable(user.getPets()).map(petService::mapPetsDTO).orElse(List.of()));
    }
}
