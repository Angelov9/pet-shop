package com.filipangelov.petshop;

import com.filipangelov.petshop.domain.HistoryLog;
import com.filipangelov.petshop.domain.User;
import com.filipangelov.petshop.dto.UserDTO;
import com.filipangelov.petshop.repository.UserRepository;
import com.filipangelov.petshop.service.HistoryLogService;
import com.filipangelov.petshop.service.PetService;
import com.filipangelov.petshop.service.UserService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PetService petService;
    @Mock
    HistoryLogService historyLogService;

    @InjectMocks
    UserService testee;

    @Test
    void testBuyPets() {
        LocalDate today = LocalDate.now();
        User user1 = User.builder()
            .id(1L)
            .firstName("First1")
            .lastName("Last1")
            .pets(new ArrayList<>())
            .emailAddress("user1@mail.com")
            .budget(10)
            .build();
        User user2 = User.builder()
            .id(2L)
            .firstName("First2")
            .lastName("Last2")
            .pets(new ArrayList<>())
            .emailAddress("user2@mail.com")
            .budget(5)
            .build();
        User user3 = User.builder()
            .id(3L)
            .firstName("First3")
            .lastName("Last3")
            .pets(new ArrayList<>())
            .emailAddress("user3@mail.com")
            .budget(15)
            .build();
        List<User> users = List.of(user1, user2, user3);

        when(userRepository.findAllByBudget()).thenReturn(users);
        when(petService.buyPet(any())).thenReturn(user1).thenReturn(user2).thenReturn(user3);
        when(userRepository.saveAll(any())).thenReturn(users);
        when(petService.usersBoughtAPetOnDate(today)).thenReturn(List.of(1L, 3L));
        when(historyLogService.userNotAllowedToBuyAnyPet(today, 1)).thenReturn(
            HistoryLog.builder().date(today).successfullyBoughtPets(2).usersNotAllowedToBuy(1).build());

        testee.buyPets();

        verify(userRepository, times(1)).findAllByBudget();
        verify(userRepository, times(1)).saveAll(any());
        verify(petService, times(3)).buyPet(any());
        verify(petService, times(1)).usersBoughtAPetOnDate(any());
        verify(historyLogService, times(1)).userNotAllowedToBuyAnyPet(today, 1);
    }

    @Test
    void testCreateRandomUsers() {
        User user1 = User.builder()
            .id(1L)
            .firstName("First1")
            .lastName("Last1")
            .pets(new ArrayList<>())
            .emailAddress("user1@mail.com")
            .budget(10)
            .build();
        User user2 = User.builder()
            .id(2L)
            .firstName("First2")
            .lastName("Last2")
            .pets(new ArrayList<>())
            .emailAddress("user2@mail.com")
            .budget(5)
            .build();
        User user3 = User.builder()
            .id(3L)
            .firstName("First3")
            .lastName("Last3")
            .pets(new ArrayList<>())
            .emailAddress("user3@mail.com")
            .budget(15)
            .build();
        List<User> users = List.of(user1, user2, user3);
        when(userRepository.saveAll(any())).thenReturn(users);

        List<UserDTO> result = testee.createRandomUsers();

        assertEquals(3, result.size());
        verify(userRepository, times(1)).saveAll(any());
    }
}
