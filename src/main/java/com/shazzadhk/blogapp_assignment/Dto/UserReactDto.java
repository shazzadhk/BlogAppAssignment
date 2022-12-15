package com.shazzadhk.blogapp_assignment.Dto;

public class UserReactDto {

    private int id;

    private int reactValue;

    private PostDto postDto;

    public UserReactDto() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReactValue() {
        return reactValue;
    }

    public void setReactValue(int reactValue) {
        this.reactValue = reactValue;
    }

    public PostDto getPostDto() {
        return postDto;
    }

    public void setPostDto(PostDto postDto) {
        this.postDto = postDto;
    }
}
