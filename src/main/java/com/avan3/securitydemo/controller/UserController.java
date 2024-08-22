package com.avan3.securitydemo.controller;

import com.avan3.securitydemo.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final List<User> users = new ArrayList<>(List.of(
            new User("test@test.com"),
            new User("admin@test.com")
    ));

    @GetMapping()
    public ResponseEntity<List<User>> getUser() {
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody User user) {
        users.add(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
