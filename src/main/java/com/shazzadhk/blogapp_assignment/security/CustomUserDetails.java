package com.shazzadhk.blogapp_assignment.security;

import com.shazzadhk.blogapp_assignment.Dao.UsersDao;
import com.shazzadhk.blogapp_assignment.entity.Users;
import com.shazzadhk.blogapp_assignment.exception.ResourceNotFoundExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomUserDetails implements UserDetailsService {

    @Autowired
    private UsersDao usersDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<Users> users = usersDao.findAll();

        return usersDao.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundExceptions("user","username: "+username,0));
    }
}
