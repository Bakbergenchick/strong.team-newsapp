package com.strongteam.newsapp.service;
import com.strongteam.newsapp.entity.Role;
import com.strongteam.newsapp.entity.Users;

import java.util.List;
import java.util.Set;

public interface UserService {
    Users register(String firstname, String surname, String email, String password, Set<String> roles);
    Users findByUserName(String username);
    Users findByUserEmail(String email);
    List<Users> getAllUsers();
}
