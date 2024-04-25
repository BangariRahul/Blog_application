package com.rahul.blog.BlogApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Likes{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer likeId;


    @ManyToOne
    private User likedBy;

    @ManyToOne
    private Post post;



}
