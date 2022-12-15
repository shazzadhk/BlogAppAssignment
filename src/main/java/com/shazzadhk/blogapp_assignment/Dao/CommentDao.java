package com.shazzadhk.blogapp_assignment.Dao;

import com.shazzadhk.blogapp_assignment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDao extends JpaRepository<Comment,Integer> {
}
