package com.strongteam.newsapp.controller;

import com.strongteam.newsapp.constants.SecurityConstant;
import com.strongteam.newsapp.entity.Users;
import com.strongteam.newsapp.exception.domain.EmailExistException;
import com.strongteam.newsapp.exception.domain.UserNotFoundException;
import com.strongteam.newsapp.payload.LoginDTO;
import com.strongteam.newsapp.payload.RegisterDTO;
import com.strongteam.newsapp.payload.UserPrincipal;
import com.strongteam.newsapp.service.UserService;
import com.strongteam.newsapp.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) throws EmailExistException {

        Users new_user = userService.register(
                registerDTO.getFirstname(),
                registerDTO.getSurname(),
                registerDTO.getEmail(),
                registerDTO.getPassword(),
                registerDTO.getRoles());

        return new ResponseEntity<>(new_user.getEmail() + " you are successfully signed up!", HttpStatus.CREATED);
//        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) throws UserNotFoundException {
        Users userByEmail = userService.findByUserEmail(loginDTO.getEmail());

        authenticate(loginDTO.getEmail(), loginDTO.getPassword());
        UserPrincipal userPrincipal = new UserPrincipal(userByEmail);
        String jwtToken = jwtTokenProvider.generateJwtToken(userPrincipal);
        HttpHeaders httpHeaders = getJwtHeader(jwtToken);
        return new ResponseEntity<>(Map.of(SecurityConstant.JWT_TOKEN_HEADER, jwtToken), httpHeaders, HttpStatus.OK);
    }

    private HttpHeaders getJwtHeader(String jwtToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(SecurityConstant.JWT_TOKEN_HEADER, jwtToken);

        return httpHeaders;
    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
