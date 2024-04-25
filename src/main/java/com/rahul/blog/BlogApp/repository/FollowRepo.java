package com.rahul.blog.BlogApp.repository;

import com.rahul.blog.BlogApp.model.Follow;
import com.rahul.blog.BlogApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepo extends JpaRepository<Follow , Integer> {

}
