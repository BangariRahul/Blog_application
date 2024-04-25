package com.rahul.blog.BlogApp.payloads;

import com.rahul.blog.BlogApp.model.Enum.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String name;

    private String username;

    private String about;

    private AccountType accountType;

    private Integer posts;

    private Integer follower;

    private Integer following;

}
