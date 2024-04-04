package com.filipangelov.petshop.repository;

import com.filipangelov.petshop.domain.Pet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findAllByOwnerIsNull();
}
