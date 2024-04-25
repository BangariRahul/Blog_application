package com.rahul.blog.BlogApp.controller;

import com.rahul.blog.BlogApp.model.User;
import com.rahul.blog.BlogApp.payloads.ApiResponse;
import com.rahul.blog.BlogApp.payloads.UserDto;
import com.rahul.blog.BlogApp.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/v1/user")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private UserService userService;


    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user , Principal principal){
        User updateduser = this.userService.updateUser(user , principal);
         return new ResponseEntity<>(updateduser, HttpStatus.OK);
    }

    // delete user
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteUser(Principal principal){
        String username = principal.getName();
        userService.deleteUser(username);
        String message = "user with username : "+ username + " deleted...";
        return new ResponseEntity<ApiResponse>(new ApiResponse(true , message) ,  HttpStatus.OK);
    }


    // get all
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAllUsers(Principal principal){

        return new ResponseEntity<>(userService.getAllUsers(principal) , HttpStatus.OK);
    }


    //get single users
    @GetMapping("/get/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){

        UserDto user = userService.getUserByID(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    //follow request
    @GetMapping("/follow/{userId}")
    public ResponseEntity<ApiResponse> followRequest(@PathVariable Integer userId ,  Principal principal){

        return userService.followRequest(userId , principal);
    }


    //show user request

    @GetMapping("/allRequests")
    public ResponseEntity<List<UserDto>> getAllRequests(Principal principal){

        return userService.getAllRequests(principal);
    }

    // show user requested list;
    @GetMapping("/requasted")
    public ResponseEntity<List<UserDto>> getAllRequeusted(Principal principal){
        return userService.getAllRequested(principal);
    }

    // followers list

    @GetMapping("/followers")
    public ResponseEntity<List<UserDto>> getAllFollowers(Principal principal){
        return userService.getAllFollowers(principal);
    }

    // following list

    @GetMapping("/following")
    public  ResponseEntity<List<UserDto>> getAllFollowing(Principal principal){
        return userService.getAllDollowing(principal);
    }


    // accept request
    @GetMapping("/acceptRequest/{userId}")
    public ResponseEntity<ApiResponse> acceptRequest(@PathVariable Integer userId , Principal principal){

        return userService.acceptRequest(userId, principal);

    }





}
