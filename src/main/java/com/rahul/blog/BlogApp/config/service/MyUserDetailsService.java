package com.rahul.blog.BlogApp.config.service;

import com.rahul.blog.BlogApp.model.User;
import com.rahul.blog.BlogApp.model.UserPrincipal;
import com.rahul.blog.BlogApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user  = userRepo.findUserByUsername(username);
        if(user == null){
            System.out.print("user not found");
            throw  new UsernameNotFoundException("user not");
        }
        return new UserPrincipal(user);
    }
}