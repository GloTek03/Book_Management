package com.ltfullstack.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ltfullstack.userservice.data.User;
import com.ltfullstack.userservice.model.UserDto;
import com.ltfullstack.userservice.service.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/api/login")
    public UserDto login(@RequestBody UserDto dto) {
        return userService.login(dto.getUsername(), dto.getPassword());
    }

    @GetMapping("/api/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/api/user")
    public User createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }
}
