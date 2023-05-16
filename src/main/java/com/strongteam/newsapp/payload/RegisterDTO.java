package com.strongteam.newsapp.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RegisterDTO {
    private String firstname;
    private String surname;
    private String email;
    private String password;
    private Set<String> roles;
}
