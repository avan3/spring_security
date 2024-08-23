package com.avan3.securitydemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/admin")
    public ResponseEntity<String> getPublic() {
        return new ResponseEntity<>("Hello Admin", HttpStatus.OK);
    }
}
