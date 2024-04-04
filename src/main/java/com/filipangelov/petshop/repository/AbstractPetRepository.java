package com.filipangelov.petshop.repository;

import com.filipangelov.petshop.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractPetRepository<T extends Pet> extends JpaRepository<T, Long> {
}
