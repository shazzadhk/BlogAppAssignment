package com.shazzadhk.blogapp_assignment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class UserReact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int reactValue;

    @ManyToOne
    @JsonIgnore
    private Post post;

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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
