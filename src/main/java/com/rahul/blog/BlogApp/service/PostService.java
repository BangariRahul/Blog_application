package com.rahul.blog.BlogApp.service;

import com.rahul.blog.BlogApp.exceptions.ResourceNotFoundException;
import com.rahul.blog.BlogApp.model.Comment;
import com.rahul.blog.BlogApp.model.Enum.AccountType;
import com.rahul.blog.BlogApp.model.Follow;
import com.rahul.blog.BlogApp.model.Post;
import com.rahul.blog.BlogApp.model.User;
import com.rahul.blog.BlogApp.payloads.ApiResponse;
import com.rahul.blog.BlogApp.payloads.Message;
import com.rahul.blog.BlogApp.payloads.PostDto;
import com.rahul.blog.BlogApp.repository.PostRepo;
import com.rahul.blog.BlogApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ObjectMapperService objectMapperService;


    public PostDto createPost(Post post , String username){

        User oldUser = userRepo.findUserByUsername(username);

        System.out.println("-------------------------------------------------------------------------------");
        post.setUser(oldUser);
        post.setAddedDate(new Date());
        postRepo.save(post);
        return objectMapperService.toPostDto(post);
    }

    public Post updatePost(Integer id , Post post){

        Post oldPost = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "postId" , id));


        oldPost.setContent(post.getContent());
        oldPost.setAddedDate(new Date());
        return postRepo.save(oldPost);
    }

    public ApiResponse deletePost(Integer id , Principal principal){


        Post oldPost = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "postId" , id));

        String username  = principal.getName();

        if(!oldPost.getUser().getUsername().equals(username)){
           return new ApiResponse( false, "you cannot delete this post.");
        }
        postRepo.deleteById(id);
        System.out.println("===============================");

        return new ApiResponse(true, "post with postId:- " + id + " deleted...");
    }


    public PostDto getPostById(Integer postId) {

        Post post = postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId" , postId));

        return objectMapperService.toPostDto(post);
    }

    public ResponseEntity<ApiResponse> postComment(Integer postId, Message message, Principal principal) {

        Post post = postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId" , postId));

        User user = userRepo.findUserByUsername(principal.getName());
        Comment comment = new Comment();
        comment.setComment(message.getText());
        comment.setPosts(post);
        comment.setUser(user);

        return new ResponseEntity<>(new ApiResponse(true, "comment added successfully......"), HttpStatus.OK);
    }

    public ResponseEntity<List<Message>> getAllComment(Integer postId, Principal principal) {
        Post post = postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId" , postId));
        User user = userRepo.findUserByUsername(principal.getName());

        User postowner = post.getUser();
        if(postowner.getAccountType() != AccountType.PUBLIC){
            List<Follow> follers = postowner.getFollowers();

            for(Follow f: follers){
                if(f.getFollower() == user){
                    break;
                }
            }
            throw new ResourceNotFoundException("you cannot access this post : follow the "+ postowner.getUsername() +" first") ;
        }
        List<Message> messages = new ArrayList<>();
        List<Comment> commentList = post.getComments();

        for(Comment c: commentList){
            messages.add(new Message(c.getUser().getUsername(), c.getComment()));
        }
        return new ResponseEntity<>(messages , HttpStatus.OK);

    }
}
