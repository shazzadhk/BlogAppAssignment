package com.shazzadhk.blogapp_assignment;

import com.shazzadhk.blogapp_assignment.Dao.RoleDao;
import com.shazzadhk.blogapp_assignment.Dao.UsersDao;
import com.shazzadhk.blogapp_assignment.entity.Role;
import com.shazzadhk.blogapp_assignment.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.Arrays;

@SpringBootApplication
public class BlogappAssignmentApplication{

//	@Autowired
//	private UsersDao usersDao;
//
//	@Autowired
//	private RoleDao roleDao;

//	@Autowired
//	private PasswordEncoder passwordEncoder;


	public static void main(String[] args) {
		SpringApplication.run(BlogappAssignmentApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		Role role = new Role(1,"ADMIN");
//		roleDao.save(role);
//
//		Users users = new Users(2,"admin","admin","Admin", Arrays.asList(role));
//		usersDao.save(users);
//	}
}
