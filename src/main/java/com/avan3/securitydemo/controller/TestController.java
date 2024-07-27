package com.avan3.securitydemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/public")
    public ResponseEntity<String> getPublic() {
        return new ResponseEntity<>("Hello Public", HttpStatus.OK);
    }

    @GetMapping("/private")
    public ResponseEntity<String> getPrivate() {
        return new ResponseEntity<>("Hello Private", HttpStatus.OK);
    }
}
