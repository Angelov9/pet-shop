package com.filipangelov.petshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Entity
@DiscriminatorValue("DOG")
public class Dog extends Pet {

    @Max(10)
    @Min(0)
    @Column(name = "rating")
    private int rating;

    @Override
    public int price() {
        return (LocalDate.now().getYear() - getDateOfBirth().getYear()) + getRating();
    }

    @Override
    public String aPetIsBought() {
        return String.format("Woof, dog %s has owner %s", getName(), getOwner().getFullName());
    }
}
