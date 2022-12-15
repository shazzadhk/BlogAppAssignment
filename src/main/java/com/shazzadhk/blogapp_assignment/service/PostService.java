package com.shazzadhk.blogapp_assignment.service;

import com.shazzadhk.blogapp_assignment.Dto.PostDto;
import com.shazzadhk.blogapp_assignment.entity.Post;

import java.util.List;

public interface PostService {

    PostDto saveNewPost(PostDto postDto,Integer userId);
    void updatePostStatus(Integer postId);
    void deletePost(Integer postId);
    List<PostDto> getAllPosts();
    List<PostDto> getOwnBlog();
    List<PostDto> getOtherBlog();
    PostDto mapPostToPostDto(Post post);
}
