package com.rahul.blog.BlogApp.service;

import com.rahul.blog.BlogApp.model.Follow;
import com.rahul.blog.BlogApp.model.Request;
import com.rahul.blog.BlogApp.model.User;
import com.rahul.blog.BlogApp.payloads.ApiResponse;
import com.rahul.blog.BlogApp.payloads.ListDto;
import com.rahul.blog.BlogApp.payloads.UserDto;
import com.rahul.blog.BlogApp.repository.FollowRepo;
import com.rahul.blog.BlogApp.repository.RequestRepo;
import com.rahul.blog.BlogApp.repository.UserRepo;
import com.rahul.blog.BlogApp.exceptions.ResourceNotFoundException;

import jdk.jfr.Unsigned;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.rahul.blog.BlogApp.model.Enum.AccountType.PUBLIC;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    private ObjectMapperService objectMapperService;

    @Autowired
    private FollowRepo followRepo;

    @Autowired
    private RequestRepo requestRepo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserDto createUser(User user) {

        System.out.println(user.getAccountType());
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);

        return objectMapperService.toUserDto(user);
    }

    public User updateUser(User user, Principal principal) {

        String username = principal.getName();
        User oldUser = userRepo.findUserByUsername(username);

        oldUser.setName(user.getName());
        oldUser.setUsername(user.getUsername());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());
        oldUser.setAbout(user.getAbout());
        return userRepo.save(oldUser);
    }


    public UserDto getUserByID(Integer userId) {

        User oldUser = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        UserDto user = objectMapperService.toUserDto(oldUser);
        return user;

    }

    public List<UserDto> getAllUsers(Principal principal) {

        String username = principal.getName();
        List<User> users = userRepo.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User u : users) {
            if (!u.getUsername().equals(username)) {
                userDtos.add(objectMapperService.toUserDto(u));
            }
        }
        return userDtos;
    }

    public void deleteUser(String username) {
        User user = userRepo.findUserByUsername(username);
        userRepo.delete(user);
        return;
    }

    public ResponseEntity<ApiResponse> followRequest(Integer userId, Principal principal) {

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        User userPrincipal = userRepo.findUserByUsername(principal.getName());

        List<Follow> followList = user.getFollowers();


        if (!(followList.size() == 0)) {


            for (Follow f : followList) {
                if (f.getFollower().equals(userPrincipal)) {
                    return new ResponseEntity<>(new ApiResponse(true, "you already follow " + user.getUsername()), HttpStatus.OK);

                }
            }

        }

        if (user.getAccountType() == PUBLIC) {
            Follow follow = new Follow();
            follow.setFollower(userPrincipal);
            follow.setUser(user);
            followRepo.save(follow);
            return new ResponseEntity<>(new ApiResponse(true, "you are following -> " + user.getUsername()), HttpStatus.OK);
        }


        Request request = new Request();
        request.setRequestedBy(userPrincipal);
        request.setRequestedTo(user);
        requestRepo.save(request);
        return new ResponseEntity<>(new ApiResponse(true, "Requested"), HttpStatus.OK);

    }


    public ResponseEntity<List<UserDto>> getAllRequests(Principal principal) {

        User user = userRepo.findUserByUsername(principal.getName());

        List<Request> requests = user.getRequests();

        List<UserDto> userDtos = new ArrayList<>();

        for (Request r : requests) {
            userDtos.add(objectMapperService.toUserDto(r.getRequestedBy()));
        }

        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    public ResponseEntity<List<UserDto>> getAllRequested(Principal principal) {
        User user = userRepo.findUserByUsername(principal.getName());

        List<Request> requests = user.getRequested();

        List<UserDto> userDtos = new ArrayList<>();

        for (Request r : requests) {
            userDtos.add(objectMapperService.toUserDto(r.getRequestedTo()));
        }

        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }


    public ResponseEntity<List<UserDto>> getAllFollowers(Principal principal) {
        User user = userRepo.findUserByUsername(principal.getName());
        List<UserDto> userDtos = new ArrayList<>();

        List<Follow> followers = user.getFollowers();

        for (Follow f : followers) {
            userDtos.add(objectMapperService.toUserDto(f.getFollower()));
        }
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    public ResponseEntity<List<UserDto>> getAllDollowing(Principal principal) {

        User user = userRepo.findUserByUsername(principal.getName());
        List<UserDto> userDtos = new ArrayList<>();
        List<Follow> followers = user.getFollowing();
        for (Follow f : followers) {
            userDtos.add(objectMapperService.toUserDto(f.getUser()));
        }
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> acceptRequest(Integer userId, Principal principal) {

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        User userPrincipal = userRepo.findUserByUsername(principal.getName());

        List<Request> requests = user.getRequests();

        if (requests.size() != 0) {

            for (Request r : requests) {
                if (r.getRequestedBy().getId() == userPrincipal.getId()) {

                    Follow follow = new Follow();
                    follow.setFollower(user);
                    follow.setUser(userPrincipal);
                    return new ResponseEntity<>(new ApiResponse(true, "request accepted"), HttpStatus.OK);
                }
            }
        }
            return new ResponseEntity<>(new ApiResponse(false, "request not found"), HttpStatus.OK);
    }
}