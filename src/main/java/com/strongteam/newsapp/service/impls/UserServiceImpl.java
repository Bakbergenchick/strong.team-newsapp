package com.strongteam.newsapp.service.impls;


import com.strongteam.newsapp.entity.Role;
import com.strongteam.newsapp.entity.Users;
import com.strongteam.newsapp.entity.enums.ERole;
import com.strongteam.newsapp.exception.domain.EmailExistException;
import com.strongteam.newsapp.exception.domain.RoleNotFoundException;
import com.strongteam.newsapp.exception.domain.UserNotFoundException;
import com.strongteam.newsapp.payload.UserPrincipal;
import com.strongteam.newsapp.repository.RoleRepository;
import com.strongteam.newsapp.repository.UserRepository;
import com.strongteam.newsapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("userDetailsService")
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Users register(String firstname, String surname, String email, String password, Set<String> roles) throws UsernameNotFoundException, EmailExistException {
        if (userRepository.findByEmail(email).isPresent()){
            throw new EmailExistException("User is already exist!");
        }

        Users user = new Users();
        user.setFirstname(firstname);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setActive(true);
        user.setNonBlocked(true);
        Set<Role> roleSet = new HashSet<>();
        if (roles == null) {
            Role userRole = roleRepository
                    .findByRoleType(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
            roleSet.add(userRole);
        } else {
            roles.forEach(r -> {
                switch (r) {
                    case "admin" -> {
                        Role adminRole = null;
                        try {
                            adminRole = roleRepository
                                    .findByRoleType(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RoleNotFoundException("Error, Role ADMIN is not found"));
                        } catch (RoleNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        roleSet.add(adminRole);
                    }
                    case "mod" -> {
                        Role modRole = null;
                        try {
                            modRole = roleRepository
                                    .findByRoleType(ERole.ROLE_MODERATOR)
                                    .orElseThrow(() -> new RoleNotFoundException("Error, Role MODERATOR is not found"));
                        } catch (RoleNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        roleSet.add(modRole);
                    }
                    default -> {
                        Role userRole = null;
                        try {
                            userRole = roleRepository
                                    .findByRoleType(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RoleNotFoundException("Error, Role USER is not found"));
                        } catch (RoleNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        roleSet.add(userRole);
                    }
                }
            });
        }

        user.setRoles(roleSet);

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User doesnt't exist!"));

        return new UserPrincipal(user);
    }

    @Override
    public Users findByUserName(String firstname) throws UserNotFoundException {
        return userRepository.findByFirstname(firstname)
                .orElseThrow(() -> new UserNotFoundException("User doesn't exist!"));
    }

    @Override
    public Users findByUserEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User doesn't exist!"));
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }


}
