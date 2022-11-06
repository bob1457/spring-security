package com.skyware.springsecurity.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest
{
    private String email;
    private String username;
    private String password;
}
