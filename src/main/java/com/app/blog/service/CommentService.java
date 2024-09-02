package com.app.blog.service;

import com.app.blog.model.Comment;
import com.app.blog.model.Post;
import com.app.blog.repository.CommentRepository;
import com.app.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Post> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow();
    }

    public Comment createComment(Comment comment, Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            comment.setPost(post);
            return commentRepository.save(comment);
        } else {
            return null;
        }
    }

    public Comment updateComment(Long commentId, Comment comment) {
        Comment existingComment = commentRepository.findById(commentId).orElseThrow();
        existingComment.setDescription(comment.getDescription());
        existingComment.setDateTime(comment.getDateTime());
        existingComment.setLikes(comment.getLikes());
        return commentRepository.save(existingComment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}

