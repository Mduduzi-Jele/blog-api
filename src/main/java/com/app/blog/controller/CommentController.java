package com.app.blog.controller;

import com.app.blog.model.Comment;
import com.app.blog.model.Post;
import com.app.blog.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/post/{post_id}/comments")
    public List<Post> getAllCommentsFromPost(@PathVariable Long post_id) {
        return commentService.getCommentsByPostId(post_id);
    }

    @GetMapping("/comment/{comment_id}")
    public Comment getCommentById(@PathVariable Long comment_id) {
        return commentService.getCommentById(comment_id);
    }

    @PostMapping("/post/{post_id}/comment")
    public Comment createComment(@RequestBody Comment comment, @PathVariable Long post_id) {
        return commentService.createComment(comment, post_id);
    }

    @PutMapping("/comment/{comment_id}")
    public Comment updateComment(@PathVariable Long comment_id, @RequestBody Comment comment) {
        return commentService.updateComment(comment_id, comment);
    }

    @DeleteMapping("/comment/{comment_id}")
    public void deleteComment(@PathVariable Long comment_id) {
        commentService.deleteComment(comment_id);
    }
}

