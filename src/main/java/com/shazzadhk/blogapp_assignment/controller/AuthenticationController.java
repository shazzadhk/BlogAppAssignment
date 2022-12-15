package com.shazzadhk.blogapp_assignment.controller;

import com.shazzadhk.blogapp_assignment.Dao.RoleDao;
import com.shazzadhk.blogapp_assignment.Dao.UsersDao;
import com.shazzadhk.blogapp_assignment.Dto.JwtAuthRequest;
import com.shazzadhk.blogapp_assignment.Dto.JwtAuthResponse;
import com.shazzadhk.blogapp_assignment.Dto.UserDto;
import com.shazzadhk.blogapp_assignment.entity.Role;
import com.shazzadhk.blogapp_assignment.entity.Users;
import com.shazzadhk.blogapp_assignment.exception.ApiException;
import com.shazzadhk.blogapp_assignment.exception.ResourceNotFoundExceptions;
import com.shazzadhk.blogapp_assignment.security.CustomUserDetails;
import com.shazzadhk.blogapp_assignment.security.JwtTokenHelper;
import com.shazzadhk.blogapp_assignment.service.UserService;
import com.shazzadhk.blogapp_assignment.service.serviceImp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserServiceImp userServiceImp;

    @Autowired
    private CustomUserDetails customUserDetails;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersDao userDao;

    @Autowired
    private RoleDao roleDao;


    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
        this.authenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = this.customUserDetails.loadUserByUsername(request.getUsername());
        String token = this.jwtTokenHelper.generateToken(userDetails);

        com.shazzadhk.blogapp_assignment.entity.Users user = userDao.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundExceptions("user","username"+request.getUsername(),0));


        Role role = roleDao.findByRoleName("BLOGGER");
        if(user.getRoles().contains(role) && !user.isApproved()){
            throw new ApiException("User is not Approved to login");
        }

        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setApproved(user.isApproved());
        userDto.setRoleList(user.getRoles());

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        response.setUserDto(userDto);
        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);

        try {

            this.authenticationManager.authenticate(authenticationToken);

        } catch (BadCredentialsException e) {
            System.out.println("Invalid Detials !!");
            throw new ApiException("Invalid username or password !!");
        }

    }

    @PostMapping("/register-admin")
    public ResponseEntity<UserDto> registerAdminUser(@Valid @RequestBody UserDto userDto){

        UserDto savedUserDto = userServiceImp.createAdminUser(userDto);
        return new ResponseEntity<UserDto>(savedUserDto, HttpStatus.CREATED);
    }

    @PostMapping("/register-blogger")
    public ResponseEntity<UserDto> registerBloggerUser(@Valid @RequestBody UserDto userDto){

        UserDto savedUserDto = userServiceImp.createBloggerUser(userDto);
        return new ResponseEntity<UserDto>(savedUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/updateUserStatus")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto){

        UserDto userDto1 = userServiceImp.UpdateUserStatus(userDto);

        return new ResponseEntity<UserDto>(userDto1,HttpStatus.OK);
    }

    @GetMapping("/all_blogger")
    public ResponseEntity<List<UserDto>> getAllBlogger(){

        List<UserDto> userDtoList = userServiceImp.getAllBlogger();

        return new ResponseEntity<List<UserDto>>(userDtoList,HttpStatus.OK);
    }

}
