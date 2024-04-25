package com.rahul.blog.BlogApp.service;

import com.rahul.blog.BlogApp.exceptions.ResourceNotFoundException;
import com.rahul.blog.BlogApp.model.Likes;
import com.rahul.blog.BlogApp.model.Post;
import com.rahul.blog.BlogApp.model.User;
import com.rahul.blog.BlogApp.payloads.ApiResponse;

import com.rahul.blog.BlogApp.payloads.PostDto;
import com.rahul.blog.BlogApp.repository.LikesRepo;
import com.rahul.blog.BlogApp.repository.PostRepo;
import com.rahul.blog.BlogApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class LikesService {

    @Autowired
    private LikesRepo likesRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;

    public ApiResponse likePost(Integer postId , Principal principal){

        User user = userRepo.findUserByUsername(principal.getName());
        Post post = postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId" , postId));

        Likes likes = new Likes();
        likes.setLikedBy(user);
        likes.setPost(post);

        likesRepo.save(likes);
        System.out.println("==============================================here========================================");
        return new ApiResponse(true ,"you liked the post( "+ postId + " ). Posted by " + user.getUsername() );

    }
}
