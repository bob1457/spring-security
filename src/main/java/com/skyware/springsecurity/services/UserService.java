package com.skyware.springsecurity.services;

import com.skyware.springsecurity.dtos.CredentialDto;
import com.skyware.springsecurity.dtos.JwtResponse;
import com.skyware.springsecurity.repository.IUserRepository;
import com.skyware.springsecurity.security.utils.JwtTokenUtil;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    private final IUserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;


    public UserService(IUserRepository userRepository, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(user.getUsername(),
                        user.getPassword(), new ArrayList<>()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//
        return userDetails;

//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found!"));
//            return (UserDetails) user;


//        if("bob".equals(username))
//        {
//            // Retrieve user information
//            return new User("bob", "{bcrypt}$2a$12$HFu4R0FGG9ZhmbzNtqtbLuv8Fdlt3yaIlSoJvESN3DKthI3iE7QwO", new ArrayList<>());
////            return new User("bob", passwordEncoder.encode("12345"), new ArrayList<>());
//        } else {
//            throw new UsernameNotFoundException("User with the username " + username + " not found");
//        }
    }


    public JwtResponse getToken(CredentialDto credentialDto) {

        final UserDetails userDetails = loadUserByUsername(credentialDto.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        return new JwtResponse(token);
    }

}