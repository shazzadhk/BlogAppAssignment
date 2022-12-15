package com.shazzadhk.blogapp_assignment.Dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class CommentDto {

    private int id;
    @NotEmpty(message = "comment content can not be empty")
    private String commentContent;

    public CommentDto(int id, String commentContent) {
        this.id = id;
        this.commentContent = commentContent;
    }

    public CommentDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

}
