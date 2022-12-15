package com.shazzadhk.blogapp_assignment.service.serviceImp;

import com.shazzadhk.blogapp_assignment.Dao.PostDao;
import com.shazzadhk.blogapp_assignment.Dao.UserReactDao;
import com.shazzadhk.blogapp_assignment.Dto.PostDto;
import com.shazzadhk.blogapp_assignment.Dto.UserReactDto;
import com.shazzadhk.blogapp_assignment.entity.Post;
import com.shazzadhk.blogapp_assignment.entity.UserReact;
import com.shazzadhk.blogapp_assignment.exception.ApiException;
import com.shazzadhk.blogapp_assignment.exception.ResourceNotFoundExceptions;
import com.shazzadhk.blogapp_assignment.service.UserReactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserReactServiceImp implements UserReactService{

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserReactDao userReactDao;

    @Autowired
    private PostServiceImp postServiceImp;

    @Override
    public UserReactDto createReactToPost(UserReactDto userReactDto, Integer postId) {

        Post post = postDao.findById(postId).
                orElseThrow(() -> new ResourceNotFoundExceptions("post","postTitle:"+postId,0));

        if(!post.isApproved()){
            throw new ApiException("Post is not approved yet. Can not give like or dislike on this post");
        }

        UserReact userReact = new UserReact();
        userReact.setReactValue(userReactDto.getReactValue());
        userReact.setPost(post);

        if(userReactDto.getReactValue() == 1){
            post.setLikeCount(post.getLikeCount() + 1);
        }else{
            post.setDislikeCount(post.getDislikeCount() + 1);
        }

        userReactDao.save(userReact);

        post.setUserReacts(Arrays.asList(userReact));

        userReactDto.setId(userReact.getId());
        PostDto postDto = postServiceImp.mapPostToPostDto(post);
        userReactDto.setPostDto(postDto);

        return userReactDto;
    }
}
