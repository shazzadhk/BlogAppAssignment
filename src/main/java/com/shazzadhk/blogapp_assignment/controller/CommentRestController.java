package com.shazzadhk.blogapp_assignment.controller;

import com.shazzadhk.blogapp_assignment.Dto.CommentDto;
import com.shazzadhk.blogapp_assignment.service.serviceImp.CommentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentRestController {

    @Autowired
    private CommentServiceImp commentServiceImp;

    @PostMapping("/create/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable Integer postId) {

        CommentDto commentDto1 = commentServiceImp.createComment(commentDto,postId);
        return new ResponseEntity<CommentDto>(commentDto1, HttpStatus.CREATED);
    }
}
