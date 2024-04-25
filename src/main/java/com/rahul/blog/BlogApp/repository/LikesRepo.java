package com.rahul.blog.BlogApp.repository;

import com.rahul.blog.BlogApp.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepo extends JpaRepository<Likes, Integer> {
}
