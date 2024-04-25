package com.rahul.blog.BlogApp.service;

import com.rahul.blog.BlogApp.model.Follow;
import com.rahul.blog.BlogApp.model.Post;
import com.rahul.blog.BlogApp.model.Request;
import com.rahul.blog.BlogApp.model.User;
import com.rahul.blog.BlogApp.payloads.ListDto;
import com.rahul.blog.BlogApp.payloads.PostDto;
import com.rahul.blog.BlogApp.payloads.UserDto;
import com.rahul.blog.BlogApp.repository.FollowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjectMapperService {

    @Autowired
    private FollowRepo followRepo;

    public UserDto toUserDto(User user){

        UserDto userDto = new UserDto();

        List<Follow> follows = followRepo.findAll();

        Integer Followers = 0;
        Integer Followings = 0;

        for(Follow f: follows){
            if(f.getFollower().getId() == user.getId()){
                Followers++;
            }
            else if(f.getUser().getId() == user.getId()){
                Followings++;
            }
        }
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setAbout(user.getAbout());
        userDto.setAbout(user.getAbout());
        userDto.setFollower(Followers);
        userDto.setFollowing(Followings);
        userDto.setAccountType(user.getAccountType());
        userDto.setPosts(user.getPosts().size());
        return userDto;
    }


    public PostDto toPostDto(Post post){

        PostDto postDto = new PostDto();

        postDto.setPostBy(post.getUser().getUsername());
        postDto.setDateOfPost(post.getAddedDate());
        postDto.setContent(post.getContent());
        postDto.setLikes(post.getLikes().size());
        postDto.setComments(post.getComments().size());
        return postDto;
    }


    public ListDto toListDto(Request request){
        ListDto listDto = new ListDto();

        listDto.setUserId(request.getRequestedBy().getId());
        listDto.setName(request.getRequestedBy().getName());
        listDto.setUsername(request.getRequestedBy().getUsername());

        return listDto;
    }

}
