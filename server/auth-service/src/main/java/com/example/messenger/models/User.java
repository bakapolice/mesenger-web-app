package com.example.messenger.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    Long id;
    String login;
    String password;
    String firstName;
    String lastName;
    String email;
    private Set<AuthRole> roles = new HashSet<>();
}
