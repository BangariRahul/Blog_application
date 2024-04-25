package com.rahul.blog.BlogApp.controller;



import com.rahul.blog.BlogApp.config.service.JwtService;
import com.rahul.blog.BlogApp.model.User;
import com.rahul.blog.BlogApp.payloads.UserDto;
import com.rahul.blog.BlogApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public UserDto register(@RequestBody User user){


        return userService.createUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if(authentication.isAuthenticated()){
            System.out.print("controller ----" + user.getUsername());
            return  jwtService.generateToken(user.getUsername());
        }
        else{
            return "Login failed";
        }
    }



}

