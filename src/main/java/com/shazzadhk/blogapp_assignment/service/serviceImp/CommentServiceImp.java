package com.shazzadhk.blogapp_assignment.service.serviceImp;

import com.shazzadhk.blogapp_assignment.Dao.CommentDao;
import com.shazzadhk.blogapp_assignment.Dao.PostDao;
import com.shazzadhk.blogapp_assignment.Dao.UsersDao;
import com.shazzadhk.blogapp_assignment.Dto.ApiResponse;
import com.shazzadhk.blogapp_assignment.Dto.CommentDto;
import com.shazzadhk.blogapp_assignment.entity.Comment;
import com.shazzadhk.blogapp_assignment.entity.Post;
import com.shazzadhk.blogapp_assignment.entity.Users;
import com.shazzadhk.blogapp_assignment.exception.ApiException;
import com.shazzadhk.blogapp_assignment.exception.ResourceNotFoundExceptions;
import com.shazzadhk.blogapp_assignment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CommentServiceImp implements CommentService{

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private PostDao postDao;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {

        Post post = postDao.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("post","post:"+postId,0));

        if(!post.isApproved()){
            throw new ApiException("Post is not approved yet. Can not comment on this post");
        }

        Comment comment = new Comment();
        comment.setCommentContent(commentDto.getCommentContent());
        comment.setPost(post);
        commentDao.save(comment);
        Set<Comment> commentSet = new HashSet<>();
        commentSet.add(comment);
        post.setComments(commentSet);
        postDao.save(post);

        return commentDto;
    }
}
