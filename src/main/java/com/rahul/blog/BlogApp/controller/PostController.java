package com.rahul.blog.BlogApp.controller;

import com.rahul.blog.BlogApp.model.Post;
import com.rahul.blog.BlogApp.payloads.ApiResponse;
import com.rahul.blog.BlogApp.payloads.Message;
import com.rahul.blog.BlogApp.payloads.PostDto;
import com.rahul.blog.BlogApp.service.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@SecurityRequirement(name = "bearerAuth")
public class PostController {


    @Autowired
    private PostService postService;
    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@RequestBody Post post , Principal principal){

        String username = principal.getName();
        PostDto createdpost = postService.createPost(post, username);

        return new ResponseEntity<>(createdpost, HttpStatus.CREATED);
    }

    @GetMapping("/get/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }

    // delete post
    @DeleteMapping("/deletePost/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId, Principal principal ){

        System.out.println("-----------------------------------------------------");
        return new ResponseEntity<ApiResponse>(postService.deletePost(postId , principal),  HttpStatus.OK);

    }

    @PostMapping("/comment/{postId}")
    public ResponseEntity<ApiResponse> PostComment(@PathVariable Integer postId , @RequestBody Message message, Principal principal){

        return postService.postComment(postId , message ,principal);
    }


    @GetMapping("/comment/get/{postId}")
    public ResponseEntity<List<Message>> getAllComments(@PathVariable Integer postId , Principal principal){
        return postService.getAllComment(postId , principal);
    }


}
