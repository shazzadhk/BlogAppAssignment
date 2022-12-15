package com.shazzadhk.blogapp_assignment.controller;

import com.shazzadhk.blogapp_assignment.Dto.ApiResponse;
import com.shazzadhk.blogapp_assignment.Dto.PostDto;
import com.shazzadhk.blogapp_assignment.service.serviceImp.PostServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostRestController {

    @Autowired
    private PostServiceImp postServiceImp;

    @PostMapping("/add_post/user/{userId}")
    public ResponseEntity<PostDto> addNewPost(@Valid @RequestBody PostDto postDto,
                                              @PathVariable Integer userId){

        return new ResponseEntity<PostDto>(postServiceImp.saveNewPost(postDto,userId), HttpStatus.CREATED);
    }

//    @PutMapping("/updatePostStatus")
//    public ResponseEntity<PostDto> updatePostStatus(@Valid @RequestBody PostDto postDto){
//
//        return new ResponseEntity<PostDto>(postServiceImp.updatePostStatus(postDto),HttpStatus.OK);
//    }

    @GetMapping("/getAllPosts")
    public ResponseEntity<List<PostDto>> allPosts(){

        List<PostDto> postDtoList = postServiceImp.getAllPosts();
        return new ResponseEntity<List<PostDto>>(postDtoList,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){

        postServiceImp.deletePost(postId);

        return new ResponseEntity<ApiResponse>(new ApiResponse("Delete post successfully",true),HttpStatus.OK);
    }

    @GetMapping("/get_own_blog")
    public ResponseEntity<List<PostDto>> showOwnBlog(){
        List<PostDto> postDtoList = postServiceImp.getOwnBlog();
        return new ResponseEntity<List<PostDto>>(postDtoList,HttpStatus.OK);
    }

    @GetMapping("/get_other_blog")
    public ResponseEntity<List<PostDto>> showOtherBlog(){
        List<PostDto> postDtoList = postServiceImp.getOtherBlog();
        return new ResponseEntity<List<PostDto>>(postDtoList,HttpStatus.OK);
    }

}
