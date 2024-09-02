package com.avan3.securitydemo.dto;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Document(collection="user")
public class User {
    private String email;
    private String password;
    private Set<String> roles;
}
