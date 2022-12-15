package com.shazzadhk.blogapp_assignment.service;

import com.shazzadhk.blogapp_assignment.Dto.CommentDto;
import org.springframework.stereotype.Service;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto,Integer postId);
}
