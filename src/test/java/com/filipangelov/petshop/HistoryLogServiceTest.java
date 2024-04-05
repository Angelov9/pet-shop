package com.filipangelov.petshop;

import com.filipangelov.petshop.domain.HistoryLog;
import com.filipangelov.petshop.repository.HistoryLogRepository;
import com.filipangelov.petshop.service.HistoryLogService;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HistoryLogServiceTest {

    @Mock
    HistoryLogRepository historyLogRepository;

    @InjectMocks
    HistoryLogService testee;

    @Test
    void testUserBoughtPet() {
        LocalDate today = LocalDate.now();
        HistoryLog historyLog =
            HistoryLog.builder().date(today).usersNotAllowedToBuy(0).successfullyBoughtPets(1).build();

        when(historyLogRepository.findByDate(today)).thenReturn(Optional.of(historyLog));
        when(historyLogRepository.save(any())).thenReturn(historyLog);

        HistoryLog result = testee.userBoughtPet(today);

        assertEquals(2, result.getSuccessfullyBoughtPets());
    }

    @Test
    void testUserBoughtPet_noLog() {
        LocalDate today = LocalDate.now();
        HistoryLog historyLog =
            HistoryLog.builder().date(today).usersNotAllowedToBuy(0).successfullyBoughtPets(1).build();

        when(historyLogRepository.findByDate(today)).thenReturn(Optional.empty());
        when(historyLogRepository.save(any())).thenReturn(historyLog);

        HistoryLog result = testee.userBoughtPet(today);

        assertEquals(1, result.getSuccessfullyBoughtPets());
    }

    @Test
    void testUserNotBoughtPet() {
        LocalDate today = LocalDate.now();
        HistoryLog historyLog =
            HistoryLog.builder().date(today).usersNotAllowedToBuy(0).successfullyBoughtPets(1).build();

        when(historyLogRepository.findByDate(today)).thenReturn(Optional.of(historyLog));
        when(historyLogRepository.save(any())).thenReturn(historyLog);

        HistoryLog result = testee.userNotAllowedToBuyAnyPet(today, 3);

        assertEquals(3, result.getUsersNotAllowedToBuy());
    }

    @Test
    void testUserNotBoughtPet_noLog() {
        LocalDate today = LocalDate.now();
        HistoryLog historyLog =
            HistoryLog.builder().date(today).usersNotAllowedToBuy(3).successfullyBoughtPets(1).build();

        when(historyLogRepository.findByDate(today)).thenReturn(Optional.empty());
        when(historyLogRepository.save(any())).thenReturn(historyLog);

        HistoryLog result = testee.userNotAllowedToBuyAnyPet(today, 3);

        assertEquals(3, result.getUsersNotAllowedToBuy());
    }
}
