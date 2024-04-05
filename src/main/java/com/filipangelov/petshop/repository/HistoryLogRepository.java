package com.filipangelov.petshop.repository;

import com.filipangelov.petshop.domain.HistoryLog;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryLogRepository extends JpaRepository<HistoryLog, Long> {

    Optional<HistoryLog> findByDate(LocalDate date);
}
