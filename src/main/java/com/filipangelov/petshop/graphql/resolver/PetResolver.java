package com.filipangelov.petshop.graphql.resolver;

import com.filipangelov.petshop.dto.PetDTO;
import com.filipangelov.petshop.service.PetService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PetResolver implements GraphQLQueryResolver {

    private final PetService petService;

    public List<PetDTO> listPets() {
        return petService.findAll();
    }

    public List<PetDTO> createPets() {
        return petService.createRandomPets();
    }
}
