package com.shazzadhk.blogapp_assignment.service.serviceImp;

import com.shazzadhk.blogapp_assignment.Dao.PostDao;
import com.shazzadhk.blogapp_assignment.Dao.RoleDao;
import com.shazzadhk.blogapp_assignment.Dao.UsersDao;
import com.shazzadhk.blogapp_assignment.Dto.PostDto;
import com.shazzadhk.blogapp_assignment.entity.*;
import com.shazzadhk.blogapp_assignment.exception.ApiException;
import com.shazzadhk.blogapp_assignment.exception.ResourceNotFoundExceptions;
import com.shazzadhk.blogapp_assignment.service.PostService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class PostServiceImp implements PostService{

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private RoleDao roleDao;


    @Override
    public PostDto saveNewPost(PostDto postDto, Integer userId) {

        Users user = usersDao.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("user","id",userId));

        Role roleAdmin = roleDao.findByRoleName("ADMIN");
        Role roleBlogger = roleDao.findByRoleName("BLOGGER");

        if(user.getRoles().contains(roleAdmin)){
            throw new ApiException("Admin Role User is not allowed to Create Post");
        }else if(user.getRoles().contains(roleBlogger) && !user.isApproved()){
            throw new ApiException("Blogger User has not approval to Create Post");
        }

        Post posts = new Post();

        posts.setPostId(postDto.getPostId());
        posts.setApproved(false);
        posts.setPostTitle(postDto.getPostTitle());
        posts.setPostContent(postDto.getPostContent());

        Date date = new Date();
        posts.setAddedDate(date);
        posts.setApproved(false);
        posts.setLikeCount(0);
        posts.setDislikeCount(0);


        posts.setUser(user);
        Set<Comment> commentSet = new HashSet<>();
        List<UserReact> userReacts = new ArrayList<>();
        commentSet.add(new Comment());
        postDto.setComments(commentSet);
        postDto.setUserReacts(userReacts);

        postDao.save(posts);


        PostDto postDto1 = new PostDto();
        postDto1.setPostId(posts.getPostId());
        postDto1.setPostTitle(posts.getPostTitle());
        postDto1.setPostContent(posts.getPostContent());
        postDto1.setAddedDate(posts.getAddedDate());
        postDto1.setLikeCount(posts.getLikeCount());
        postDto1.setDislikeCount(posts.getDislikeCount());

        return postDto1;
    }

    @Override
    public void updatePostStatus(Integer postId) {
        Post post = postDao.findById(postId).
                orElseThrow(() -> new ResourceNotFoundExceptions("post","postTitle:"+postId,0));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean hasRoleBlogger = auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("BLOGGER"));
        if(hasRoleBlogger){
            throw new ApiException("Only Admin User can update Post status");
        }

        post.setApproved(true);
        postDao.save(post);

    }

    @Override
    public void deletePost(Integer postId) {

        Post post = postDao.findById(postId).
                orElseThrow(() -> new ResourceNotFoundExceptions("post","post:"+postId,0));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean hasRoleBlogger = auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("BLOGGER"));

        if(hasRoleBlogger && !auth.getName().equals(post.getUser().getUsername())){
            throw new ApiException("User : "+auth.getName()+" is not allowed to delete post");
        }

        postDao.delete(post);

    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> postList = postDao.findAll()
                .stream().filter(post -> !post.isApproved()).collect(Collectors.toList());

        List<PostDto> postDtoList = new ArrayList<>();
        for(Post p: postList){
            PostDto postDto = this.mapPostToPostDto(p);
            postDtoList.add(postDto);
        }

        return postDtoList;
    }

    @Override
    public List<PostDto> getOwnBlog() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<Post> postList = postDao.findAll()
                .stream()
                .filter(post -> post.getUser().getUsername().equals(auth.getName()))
                .collect(Collectors.toList());

        List<PostDto> postDtoList = new ArrayList<>();
        for(Post p: postList){
            PostDto postDto = this.mapPostToPostDto(p);
            postDtoList.add(postDto);
        }
        return postDtoList;
    }

    @Override
    public List<PostDto> getOtherBlog() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<Post> postList = postDao.findAll()
                .stream()
                .filter(post -> !post.getUser().getUsername().equals(auth.getName()))
                .filter(post -> post.isApproved())
                .collect(Collectors.toList());

        List<PostDto> postDtoList = new ArrayList<>();
        for(Post p: postList){
            PostDto postDto = this.mapPostToPostDto(p);
            postDtoList.add(postDto);
        }
        return postDtoList;
    }

    @Override
    public PostDto mapPostToPostDto(Post p) {
        PostDto postDto = new PostDto();
        postDto.setPostId(p.getPostId());
        postDto.setPostContent(p.getPostContent());
        postDto.setPostTitle(p.getPostTitle());
        postDto.setApproved(p.isApproved());
        postDto.setAddedDate(p.getAddedDate());
        postDto.setComments(p.getComments());
        postDto.setUserReacts(p.getUserReacts());
        postDto.setLikeCount(p.getLikeCount());
        postDto.setDislikeCount(p.getDislikeCount());
        return postDto;
    }

}
