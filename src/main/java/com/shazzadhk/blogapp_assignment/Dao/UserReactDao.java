package com.shazzadhk.blogapp_assignment.Dao;

import com.shazzadhk.blogapp_assignment.entity.UserReact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReactDao extends JpaRepository<UserReact,Integer> {
}
