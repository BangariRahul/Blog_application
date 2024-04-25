package com.rahul.blog.BlogApp.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListDto {

    private Integer userId;
    private String name;
    private String username;


}
