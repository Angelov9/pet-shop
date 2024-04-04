package com.filipangelov.petshop.api;

import com.filipangelov.petshop.dto.UserDTO;
import com.filipangelov.petshop.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/create-users")
    public List<UserDTO> createRandomUsers() {
        return userService.createRandomUsers();
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping("/create")
    public UserDTO createOrUpdateUser(@RequestBody UserDTO userDTO) {
        return userService.createOrUpdateUser(userDTO);
    }

    @GetMapping("/buy-pets")
    public void buyPets() {
        userService.buyPets();
    }
}
