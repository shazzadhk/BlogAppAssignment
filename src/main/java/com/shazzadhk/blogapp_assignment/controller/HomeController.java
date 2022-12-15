package com.shazzadhk.blogapp_assignment.controller;

import com.shazzadhk.blogapp_assignment.Dao.PostDao;
import com.shazzadhk.blogapp_assignment.Dto.PostDto;
import com.shazzadhk.blogapp_assignment.Dto.UserDto;
import com.shazzadhk.blogapp_assignment.entity.Post;
import com.shazzadhk.blogapp_assignment.service.serviceImp.PostServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private PostDao postDao;

    @Autowired
    private PostServiceImp postServiceImp;

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    //home page of blog app
    @RequestMapping("/home")
    public String home(Model model){
        List<Post> postList = postDao.findAll();
        model.addAttribute("posts",postList);
        model.addAttribute("isPostEmpty",postList.isEmpty());
        return "home";
    }

    //all post list
    @RequestMapping("/all_post_list")
    public String showPosts(Model model){
        String uri = "http://localhost:9797/api/post/getAllPosts";
        RestTemplate restTemplate = new RestTemplate();
        PostDto[] postDtos = restTemplate.getForObject(uri,PostDto[].class);

        model.addAttribute("postList", Arrays.asList(postDtos));

        return "post_list";
    }

    //update post status
    @RequestMapping("/website/updatePostStatus")
    public String updatePostStatus(@RequestParam("postId")Integer postId){

        postServiceImp.updatePostStatus(postId);

        return "redirect:/all_post_list";
    }

    //show own blog
    @RequestMapping("/view_own_post")
    public String showOwnBlog(Model model){

//        String uri = "http://localhost:9797/api/post/get_own_blog";
//        RestTemplate restTemplate = new RestTemplate();
//        PostDto[] postDtos = restTemplate.getForObject(uri,PostDto[].class);
//
//        model.addAttribute("postList", Arrays.asList(postDtos));

        List<PostDto> ownBlog = postServiceImp.getOwnBlog();
        model.addAttribute("postList", ownBlog);


        return "show_blog";
    }

    @RequestMapping("/view_other_post")
    public String showOtherBlog(Model model){

//        String uri = "http://localhost:9797/api/post/get_other_blog";
//        RestTemplate restTemplate = new RestTemplate();
//        PostDto[] postDtos = restTemplate.getForObject(uri,PostDto[].class);
//
        List<PostDto> otherBlog = postServiceImp.getOtherBlog();
        model.addAttribute("postList", otherBlog);

        return "show_blog";
    }

    @RequestMapping("/deletePost")
    public String blogPost(@RequestParam("postId")Integer postId){

        postServiceImp.deletePost(postId);

        return "redirect:/all_post_list";
    }

    @RequestMapping("/view_own_post_list")
    public String viewOwnPostList(Model model){
        List<PostDto> ownBlog = postServiceImp.getOwnBlog();
        model.addAttribute("postList", ownBlog);

        return "current_blogger_post_list";
    }
}
