package com.rahul.blog.BlogApp.controller;

import com.rahul.blog.BlogApp.payloads.ApiResponse;
import com.rahul.blog.BlogApp.service.LikesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/like")
@SecurityRequirement(name = "bearerAuth")
public class LikesController {

    @Autowired
    private LikesService likesService;
    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> likePost(@PathVariable Integer postId , Principal principal){

        System.out.println("heree in like post classss---------------------------------");
        return new ResponseEntity<>(likesService.likePost(postId, principal), HttpStatus.OK);

    }

}
