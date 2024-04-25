package com.rahul.blog.BlogApp.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor@NoArgsConstructor
public class PostDto {

    private String postBy;
    private String content;

    private Date dateOfPost;

    private Integer likes;

    private Integer comments;


}
