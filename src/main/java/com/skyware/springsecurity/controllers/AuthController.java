package com.skyware.springsecurity.controllers;


import com.skyware.springsecurity.dtos.CredentialDto;
import com.skyware.springsecurity.dtos.JwtResponse;
import com.skyware.springsecurity.dtos.SignUpRequest;
import com.skyware.springsecurity.models.User;
import com.skyware.springsecurity.repository.IUserRepository;
import com.skyware.springsecurity.services.UserService;
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
    private IUserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;


//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    public AuthController(UserService userService, IUserRepository userRepository, AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody CredentialDto credentialDto) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(credentialDto.getUsername(), credentialDto.getPassword()));

        var token = userService.getToken(credentialDto);

        return ResponseEntity.ok(token);

    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> register(@RequestBody SignUpRequest request)
    {
        if(userRepository.existsByEmail(request.getEmail()))
        {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email already exists!" );
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        User user = new User(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}
