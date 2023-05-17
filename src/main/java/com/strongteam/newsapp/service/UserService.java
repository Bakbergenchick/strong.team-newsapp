package com.strongteam.newsapp.service;
import com.strongteam.newsapp.entity.Role;
import com.strongteam.newsapp.entity.Users;
import com.strongteam.newsapp.exception.domain.EmailExistException;
import com.strongteam.newsapp.exception.domain.UserNotFoundException;

import java.util.List;
import java.util.Set;

public interface UserService {
    Users register(String firstname, String surname, String email, String password, Set<String> roles) throws EmailExistException;
    Users findByUserName(String username) throws UserNotFoundException;
    Users findByUserEmail(String email) throws UserNotFoundException;
    List<Users> getAllUsers();
}
