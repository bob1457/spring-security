package com.skyware.springsecurity.controllers;


import com.skyware.springsecurity.dtos.CredentialDto;
import com.skyware.springsecurity.dtos.JwtResponse;
import com.skyware.springsecurity.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController
{
    private UserService userService;
    private AuthenticationManager authenticationManager;


//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody CredentialDto credentialDto) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(credentialDto.getUsername(), credentialDto.getPassword()));

        var token = userService.getToken(credentialDto);

        return ResponseEntity.ok(token);

    }
}
