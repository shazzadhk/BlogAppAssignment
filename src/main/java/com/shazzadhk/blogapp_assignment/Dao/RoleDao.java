package com.shazzadhk.blogapp_assignment.Dao;

import com.shazzadhk.blogapp_assignment.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role,Integer> {
}
