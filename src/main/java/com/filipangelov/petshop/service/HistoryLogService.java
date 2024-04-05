package com.filipangelov.petshop.service;

import com.filipangelov.petshop.domain.HistoryLog;
import com.filipangelov.petshop.repository.HistoryLogRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HistoryLogService {

    private final HistoryLogRepository historyLogRepository;

    public HistoryLog userBoughtPet(LocalDate date) {
        return historyLogRepository.save(historyLogRepository.findByDate(date).map(log -> {
            log.setSuccessfullyBoughtPets(log.getSuccessfullyBoughtPets() + 1);
            return log;
        }).orElse(HistoryLog.builder()
            .date(date)
            .successfullyBoughtPets(1)
            .usersNotAllowedToBuy(0)
            .build()));
    }

    public HistoryLog userNotAllowedToBuyAnyPet(LocalDate date, int numberOfUsers) {
        return historyLogRepository.save(historyLogRepository.findByDate(date).map(log -> {
            log.setUsersNotAllowedToBuy(numberOfUsers);
            return log;
        }).orElse(HistoryLog.builder()
            .date(date)
            .successfullyBoughtPets(0)
            .usersNotAllowedToBuy(numberOfUsers)
            .build()));
    }
}
