package com.avan3.securitydemo.controller;

import com.avan3.securitydemo.dto.User;
import com.avan3.securitydemo.service.UserDetailsService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping()
    public ResponseEntity<List<User>> getUser() {
        List<User> users = userDetailsService.loadAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody User user) throws BadRequestException {
        if (null == user) {
            throw new BadRequestException("User from request is null!");
        }
        User returnedUser = userDetailsService.saveUser(user);
        return new ResponseEntity<>(returnedUser, HttpStatus.OK);
    }
}
