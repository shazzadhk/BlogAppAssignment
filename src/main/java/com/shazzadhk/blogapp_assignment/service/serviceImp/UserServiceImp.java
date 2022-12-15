package com.shazzadhk.blogapp_assignment.service.serviceImp;

import com.shazzadhk.blogapp_assignment.Dao.RoleDao;
import com.shazzadhk.blogapp_assignment.Dao.UsersDao;
import com.shazzadhk.blogapp_assignment.Dto.UserDto;
import com.shazzadhk.blogapp_assignment.entity.Role;
import com.shazzadhk.blogapp_assignment.entity.Users;
import com.shazzadhk.blogapp_assignment.exception.ApiException;
import com.shazzadhk.blogapp_assignment.exception.ResourceNotFoundExceptions;
import com.shazzadhk.blogapp_assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createAdminUser(UserDto userDto) {
        Role role = roleDao.findByRoleName("ADMIN");

        Users users = new Users();
        users.setName(userDto.getName());
        users.setUsername(userDto.getUsername());
        users.setPassword(passwordEncoder.encode(userDto.getPassword()));
        users.setApproved(true);
        users.setRoles(Arrays.asList(role));
        usersDao.save(users);

        userDto.setRoleList(users.getRoles());

        return userDto;
    }

    @Override
    public UserDto createBloggerUser(UserDto userDto) {
        Role role = roleDao.findByRoleName("BLOGGER");

        Users users = new Users();
        users.setName(userDto.getName());
        users.setUsername(userDto.getUsername());
        users.setPassword(passwordEncoder.encode(userDto.getPassword()));
        users.setApproved(false);
        users.setRoles(Arrays.asList(role));
        usersDao.save(users);

        userDto.setId(users.getId());
        userDto.setRoleList(users.getRoles());
        userDto.setApproved(users.isApproved());

        return userDto;
    }

    @Override
    public UserDto UpdateUserStatus(UserDto userDto) {

        Users user = usersDao.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundExceptions("user","username"+userDto.getUsername(),0));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean hasRoleBlogger = auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("BLOGGER"));
        if(hasRoleBlogger){
            throw new ApiException("Only Admin User can update User status");
        }


        user.setApproved(userDto.isApproved());
        usersDao.save(user);

        userDto.setRoleList(user.getRoles());
        userDto.setApproved(user.isApproved());
        return userDto;
    }

    @Override
    public List<UserDto> getAllBlogger() {

        Role roleBlogger = roleDao.findByRoleName("BLOGGER");

        List<Users> usersList = usersDao.findAll();
        List<Users> filterUser = usersList.stream().
//                filter(users -> users.isApproved()).
                filter(users -> users.getRoles().contains(roleBlogger)).
                collect(Collectors.toList());

        List<UserDto> userDtoList = new ArrayList<>();

        for(Users u : filterUser){
            UserDto userDto = new UserDto();
            userDto.setId(u.getId());
            userDto.setName(u.getName());
            userDto.setUsername(u.getUsername());
            userDto.setRoleList(u.getRoles());
            userDto.setApproved(u.isApproved());
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Override
    public void updateBloggerStatus(String username,boolean status) {

        Users user = usersDao.findByUsername((username))
                .orElseThrow(() -> new ResourceNotFoundExceptions("user","username"+username,0));
        user.setApproved(!status);
        usersDao.save(user);
    }

}
