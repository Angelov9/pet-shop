package com.filipangelov.petshop.graphql.resolver;

import com.filipangelov.petshop.dto.UserDTO;
import com.filipangelov.petshop.service.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserResolver implements GraphQLQueryResolver {

    private final UserService userService;

    public List<UserDTO> listUsers() {
        return userService.findAll();
    }

    public List<UserDTO> createUsers() {
        return userService.createRandomUsers();
    }

}
