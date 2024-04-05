package com.filipangelov.petshop;

import com.filipangelov.petshop.domain.Cat;
import com.filipangelov.petshop.domain.Dog;
import com.filipangelov.petshop.domain.Pet;
import com.filipangelov.petshop.domain.User;
import com.filipangelov.petshop.domain.enums.PetType;
import com.filipangelov.petshop.repository.CatRepository;
import com.filipangelov.petshop.repository.DogRepository;
import com.filipangelov.petshop.service.factory.PetFactory;
import java.time.LocalDate;
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
public class PetFactoryTest {

    @Mock
    CatRepository catRepository;
    @Mock
    DogRepository dogRepository;

    @InjectMocks
    PetFactory testee;

    @Test
    void testCreatePet_Cat() {
        Cat cat = Cat.builder().name("Cat").description("Cat description").dateOfBirth(LocalDate.now()).build();
        when(catRepository.save(any())).thenReturn(cat);

        Pet result = testee.createPet(
            PetType.CAT,
            "Cat",
            "Cat description",
            LocalDate.now()
        );

        assertEquals(PetType.CAT.name(), result.petType());
        verify(catRepository, times(1)).save(any());
    }

    @Test
    void testCreatePet_Dog() {
        Dog dog = Dog.builder().name("Dog").description("Dog description").dateOfBirth(LocalDate.now()).build();
        when(dogRepository.save(any())).thenReturn(dog);

        Pet result = testee.createPet(
            PetType.DOG,
            "Dog",
            "Dog description",
            LocalDate.now()
        );

        assertEquals(PetType.DOG.name(), result.petType());
        verify(dogRepository, times(1)).save(any());
    }

    @Test
    void testUpdatePet_Cat() {
        Cat cat = Cat.builder()
            .name("Cat")
            .description("Cat description")
            .dateOfBirth(LocalDate.now().minusYears(2))
            .owner(new User())
            .dateOfOwnerShip(LocalDate.now())
            .build();

        when(catRepository.save(any())).thenReturn(cat);

        testee.updatePet(cat);

        verify(catRepository, times(1)).save(any());
    }

    @Test
    void testUpdatePet_Dog() {
        Dog dog = Dog.builder()
            .name("Dog")
            .description("Dog description")
            .dateOfBirth(LocalDate.now().minusYears(2))
            .owner(new User())
            .dateOfOwnerShip(LocalDate.now())
            .build();

        when(dogRepository.save(any())).thenReturn(dog);

        testee.updatePet(dog);

        verify(dogRepository, times(1)).save(any());
    }
}
