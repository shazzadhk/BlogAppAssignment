package com.shazzadhk.blogapp_assignment.Dto;

import com.shazzadhk.blogapp_assignment.entity.Comment;
import com.shazzadhk.blogapp_assignment.entity.UserReact;

import javax.validation.constraints.NotEmpty;
import java.util.*;


public class PostDto {

    private int postId;

    @NotEmpty(message = "post title can not be empty")
    private String postTitle;

    @NotEmpty(message = "post title can not be empty")
    private String postContent;

    private Date addedDate;

    private boolean isApproved;

    private int likeCount;

    private int dislikeCount;

    private Set<Comment> comments = new HashSet<>();

    private List<UserReact> userReacts = new ArrayList<>();

    public PostDto() {
    }

    public PostDto(int postId, String postTitle, String postContent, Date addedDate, boolean isApproved, Set<Comment> comments, List<UserReact> userReacts) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.addedDate = addedDate;
        this.isApproved = isApproved;
        this.comments = comments;
        this.userReacts = userReacts;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }


    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public List<UserReact> getUserReacts() {
        return userReacts;
    }

    public void setUserReacts(List<UserReact> userReacts) {
        this.userReacts = userReacts;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }
}
