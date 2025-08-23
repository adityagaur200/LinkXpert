package com.example.PostService.Controller;

import com.example.PostService.Model.Post;
import com.example.PostService.Service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    //Injecting PostService
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(
            @RequestParam Long user_id,
            @RequestParam String content) {
        Post post = postService.createPost(user_id,new org.bson.types.Binary(content.getBytes()));
        return ResponseEntity.ok(post);
    }

    @PostMapping("/like")
    public ResponseEntity<Post> likePost(@RequestParam Long user_id,@RequestParam String post_id)
    {
        Post post =postService.likePost(user_id,post_id);
        return ResponseEntity.ok(post);
    }
}
