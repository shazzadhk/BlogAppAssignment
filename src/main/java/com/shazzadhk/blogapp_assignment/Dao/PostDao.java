package com.shazzadhk.blogapp_assignment.Dao;

import com.shazzadhk.blogapp_assignment.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDao extends JpaRepository<Post,Integer>{
}
