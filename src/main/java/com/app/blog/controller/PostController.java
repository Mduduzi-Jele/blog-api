package com.app.blog.controller;

import com.app.blog.model.Post;
import com.app.blog.service.PostService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/user/{id}/posts")
    public Iterable<Post> getAllPostsFromUser(@PathVariable Long id) {
        return postService.getPostsByUserId(id);
    }

    @GetMapping("/post/{post_id}")
    public Post getPostById(@PathVariable Long post_id) {
        return postService.getPostById(post_id);
    }

    @PostMapping("/user/{user_id}/post")
    public Post createPost(@RequestPart("imageUrl") MultipartFile file,
                           @RequestParam("title") String title,
                           @RequestParam("description") String description,
                           @RequestParam("dataTime") Date dataTime,
                           @RequestParam("views") Integer views,
                           @RequestParam("likes") Integer likes,
                           @PathVariable Long user_id) throws IOException {
        return postService.createPost(file, title, description, dataTime, views, likes, user_id);
    }

    @PutMapping("/post/{post_id}")
    public Post updatePost(@PathVariable Long post_id, @RequestBody Post post) {
        return postService.updatePost(post_id, post);
    }

    @PutMapping("/view/{post_id}")
    public Post view(@PathVariable Long post_id, @RequestBody Post post) {
        return postService.updatePostViews(post_id, post.getViews());
    }

    @PutMapping("/like/{post_id}")
    public Post like(@PathVariable Long post_id, @RequestBody Post post) {
        return postService.updatePostLikes(post_id, post.getLikes());
    }

    @Transactional
    @DeleteMapping("/post/{post_id}")
    public void deletePost(@PathVariable Long post_id) {
        postService.deletePost(post_id);
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") long id) {
        try {
            byte[] image = postService.getImage(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

