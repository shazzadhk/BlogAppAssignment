package com.shazzadhk.blogapp_assignment.service;

import com.shazzadhk.blogapp_assignment.Dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createAdminUser(UserDto userDto);
    UserDto createBloggerUser(UserDto userDto);
    UserDto UpdateUserStatus(UserDto userDto);
    List<UserDto> getAllBlogger();
    void updateBloggerStatus(String username,boolean status);

}
