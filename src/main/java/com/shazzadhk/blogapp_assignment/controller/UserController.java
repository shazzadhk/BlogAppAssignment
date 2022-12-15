package com.shazzadhk.blogapp_assignment.controller;

import com.shazzadhk.blogapp_assignment.Dto.UserDto;
import com.shazzadhk.blogapp_assignment.entity.Users;
import com.shazzadhk.blogapp_assignment.service.serviceImp.UserServiceImp;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserServiceImp userServiceImp;


    @RequestMapping("/add_blogger")
    public String add_blogger(Model model){
        model.addAttribute("bloggers",new Users());
        return "add_blogger";
    }

    @RequestMapping("/website/all_blogger_list")
    public String bloggerListPage(Model model){

        String uri = "http://localhost:9797/api/auth/all_blogger";
        RestTemplate restTemplate = new RestTemplate();
        UserDto[] userDtoList = restTemplate.getForObject(uri,UserDto[].class);

        model.addAttribute("bloggerList", Arrays.asList(userDtoList));

        return "blogger_list";
    }

    @RequestMapping("/website/updateStatus")
    public String updateBloggerStatus(@RequestParam("username")String username,@RequestParam("status") boolean status){

        userServiceImp.updateBloggerStatus(username,status);

        return "redirect:/all_blogger_list";
    }

}
