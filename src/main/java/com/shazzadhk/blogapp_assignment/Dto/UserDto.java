package com.shazzadhk.blogapp_assignment.Dto;

import com.shazzadhk.blogapp_assignment.entity.Role;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class UserDto {

    private int id;

    @NotEmpty(message = "Name can not be empty")
    @Size(min = 4,message = "Name must be minimum of 4 characters")
    private String name;

    @NotEmpty(message = "Username can not be empty")
    private String username;

    @Size(min = 6,max = 10,message = "password must be between 6 to 10 characters")
    @NotEmpty(message = "Password can not be empty")
    private String password;

    private boolean isApproved;

    List<Role> roleList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
