package com.filipangelov.petshop.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@Getter
@Entity
@DiscriminatorValue("CAT")
public class Cat extends Pet {

    @Override
    public int price() {
        return LocalDate.now().getYear() - getDateOfBirth().getYear();
    }

    @Override
    public String aPetIsBought() {
        return String.format("Meow, cat %s has owner %s", getName(), getOwner().getFullName());
    }
}
