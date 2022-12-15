package com.shazzadhk.blogapp_assignment.service;

import com.shazzadhk.blogapp_assignment.Dto.UserReactDto;

public interface UserReactService {

    UserReactDto createReactToPost(UserReactDto userReactDto,Integer postId);
}
