package com.filipangelov.petshop;

import com.filipangelov.petshop.domain.Cat;
import com.filipangelov.petshop.domain.Dog;
import com.filipangelov.petshop.domain.HistoryLog;
import com.filipangelov.petshop.domain.Pet;
import com.filipangelov.petshop.domain.User;
import com.filipangelov.petshop.dto.PetDTO;
import com.filipangelov.petshop.repository.PetRepository;
import com.filipangelov.petshop.service.HistoryLogService;
import com.filipangelov.petshop.service.PetService;
import com.filipangelov.petshop.service.factory.PetFactory;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {

    @Mock
    PetRepository petRepository;
    @Mock
    PetFactory petFactory;
    @Mock
    HistoryLogService historyLogService;

    @InjectMocks
    PetService testee;

    @Test
    void testBuyPet() {
        Pet cat = Cat.builder()
            .id(1L)
            .name("Cat")
            .dateOfBirth(LocalDate.now().minusYears(2))
            .description("Cat description")
            .build();
        Pet dog = Dog.builder()
            .id(2L)
            .name("Dog")
            .dateOfBirth(LocalDate.now().minusYears(3))
            .description("Dog description")
            .rating(4)
            .build();
        List<Pet> pets = List.of(cat, dog);

        User user = User.builder()
            .id(1L)
            .firstName("First")
            .lastName("Last")
            .budget(10)
            .emailAddress("user@mail.com")
            .build();

        when(petRepository.findAllByOwnerIsNull()).thenReturn(pets);
        when(petFactory.updatePet(any())).thenReturn(cat).thenReturn(dog);
        when(historyLogService.userBoughtPet(any())).thenReturn(
            HistoryLog.builder()
                .id(1L)
                .successfullyBoughtPets(1)
                .usersNotAllowedToBuy(0)
                .build());

        User result = testee.buyPet(user);

        assertEquals(1, result.getBudget());
        verify(petFactory, times(2)).updatePet(any());
    }

    @Test
    void testCreateRandomPets() {
        Pet cat = Cat.builder()
            .id(1L)
            .name("Cat1")
            .dateOfBirth(LocalDate.now().minusYears(2))
            .description("Cat1 description")
            .build();
        Pet cat2 = Cat.builder()
            .id(3L)
            .name("Cat3")
            .dateOfBirth(LocalDate.now().minusYears(2))
            .description("Cat3 description")
            .build();
        Pet dog = Dog.builder()
            .id(2L)
            .name("Dog2")
            .dateOfBirth(LocalDate.now().minusYears(3))
            .description("Dog2 description")
            .rating(4)
            .build();

        when(petFactory.createPet(any(), any(), any(), any())).thenReturn(cat).thenReturn(dog).thenReturn(cat2);

        List<PetDTO> result = testee.createRandomPets();

        assertFalse(result.isEmpty());
        verify(petFactory, times(result.size())).createPet(any(), any(), any(), any());
    }
}
