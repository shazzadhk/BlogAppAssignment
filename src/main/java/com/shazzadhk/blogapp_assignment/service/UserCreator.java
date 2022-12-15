package com.shazzadhk.blogapp_assignment.service;

import com.shazzadhk.blogapp_assignment.Dao.UsersDao;
import com.shazzadhk.blogapp_assignment.entity.Role;
import com.shazzadhk.blogapp_assignment.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class UserCreator {

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        Role role = new Role();
        role.setRoleName("ADMIN");
        Users users = new Users("admin",passwordEncoder.encode("admin1"),"Admin", true,Arrays.asList(role));
        usersDao.save(users);
    }
}
