package com.shazzadhk.blogapp_assignment.controller;

import com.shazzadhk.blogapp_assignment.Dto.CommentDto;
import com.shazzadhk.blogapp_assignment.Dto.UserReactDto;
import com.shazzadhk.blogapp_assignment.service.serviceImp.UserReactServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/react")
public class UserReactRestController {

    @Autowired
    private UserReactServiceImp userReactServiceImp;

    @PostMapping("/create/{postId}")
    public ResponseEntity<UserReactDto> createUserReact(@RequestBody UserReactDto userReactDto,
            @PathVariable Integer postId) {

        UserReactDto userReactDto1 = userReactServiceImp.createReactToPost(userReactDto,postId);
        return new ResponseEntity<UserReactDto>(userReactDto1, HttpStatus.CREATED);
    }

}
