package com.filipangelov.petshop.repository;

import com.filipangelov.petshop.domain.Pet;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findAllByOwnerIsNull();

    List<Pet> findAllByDateOfOwnerShip(LocalDate date);
}
