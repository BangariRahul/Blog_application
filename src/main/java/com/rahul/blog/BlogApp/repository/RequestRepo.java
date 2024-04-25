package com.rahul.blog.BlogApp.repository;

import com.rahul.blog.BlogApp.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepo extends JpaRepository<Request, Integer> {
}
