package com.rahul.blog.BlogApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rahul.blog.BlogApp.model.Enum.AccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @NotBlank
//    @Size(min =4 , message = "Username must be min of 4 characters !!")
    private String name;

    private String username;

//    @Email(message = "Email address is not valid !!")
    private String email;

//    @NotEmpty
//    @Size(min =3 , max =10 , message = "Password must be minimum of 3 chars and max of 10 chars !!")

     private String password;

//    @NotNull
    private String about;


    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL , fetch = FetchType.LAZY )
    @JsonIgnore
    private List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Follow> following = new ArrayList<>();

    @OneToMany(mappedBy = "requestedTo", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Request> requests = new ArrayList<>();


    @OneToMany(mappedBy = "requestedBy", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Request> requested = new ArrayList<>();
}
