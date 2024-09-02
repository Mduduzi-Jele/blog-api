package com.app.blog.repository;

import com.app.blog.model.Comment;
import com.app.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Post> findByPostId(Long postId);

    void deleteCommentsByPostId(Long postId);
}
