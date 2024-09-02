package com.app.blog.service;

import com.app.blog.model.Post;
import com.app.blog.model.User;
import com.app.blog.repository.CommentRepository;
import com.app.blog.repository.PostRepository;
import com.app.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private final String uploadDirectory = "/tmp/uploads/";

    public Resource loadImageAsResource(String imageName) throws IOException {
        Resource resource = resourceLoader.getResource("file:" + uploadDirectory + imageName);
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new IOException("Could not read the image file: " + imageName);
        }
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Iterable<Post> getPostsByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow();
    }

    public Post createPost(MultipartFile file, String title, String description, Date dataTime, Integer views, Integer likes, Long userId) throws IOException {
        Path uploadPath = Paths.get(uploadDirectory);

        // Ensure the directory exists
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path imagePath = uploadPath.resolve(file.getOriginalFilename());
        Files.write(imagePath, file.getBytes());

        Post myPost = new Post();
        myPost.setTitle(title);
        myPost.setDescription(description);
        myPost.setImageUrl(file.getOriginalFilename());
        myPost.setLikes(likes);
        myPost.setViews(views);
        myPost.setDataTime(dataTime);

        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            myPost.setUser(user);
            return postRepository.save(myPost);
        } else {
            return null;
        }
    }

    public Post updatePost(Long postId, Post post) {
        Post existingPost = postRepository.findById(postId).orElseThrow();
        existingPost.setTitle(post.getTitle());
        existingPost.setDescription(post.getDescription());
        return postRepository.save(existingPost);
    }

    public Post updatePostViews(Long postId, Integer views) {
        Post existingPost = postRepository.findById(postId).orElseThrow();
        existingPost.setViews(views);
        return postRepository.save(existingPost);
    }

    public Post updatePostLikes(Long postId, Integer likes) {
        Post existingPost = postRepository.findById(postId).orElseThrow();
        existingPost.setLikes(likes);
        return postRepository.save(existingPost);
    }

    @Transactional
    public void deletePost(Long postId) {
        commentRepository.deleteCommentsByPostId(postId);
        Optional<Post> post = postRepository.findById(postId);
        post.ifPresent(postRepository::delete);
    }

    public byte[] getImage(Long id) throws IOException {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            String imageName = post.get().getImageUrl();
            return Files.readAllBytes(Paths.get(uploadDirectory + imageName));
        }
        return new byte[0];
    }
}

