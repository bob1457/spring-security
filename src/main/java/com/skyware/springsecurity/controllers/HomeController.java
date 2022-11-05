package com.skyware.springsecurity.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController
{
    @GetMapping("/hello")
    public String Hello()
    {
        return "Hello world!";
    }
}
