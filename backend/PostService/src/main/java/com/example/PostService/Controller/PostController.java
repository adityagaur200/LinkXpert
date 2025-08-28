package com.example.PostService.Controller;

import com.example.PostService.DTO.CommentDTO;
import com.example.PostService.DTO.CreateDTO;
import com.example.PostService.DTO.ReplyDTO;
import com.example.PostService.DTO.RequestDTO;
import com.example.PostService.Model.Post;
import com.example.PostService.Service.CommentsService;
import com.example.PostService.Service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final CommentsService commentsService;

    //Injecting PostService
    public PostController(PostService postService, CommentsService commentsService) {
        this.postService = postService;
        this.commentsService = commentsService;
    }


    //Controller for create post.
    @PostMapping("/create")
    public ResponseEntity<Post> createPost(
            @RequestBody CreateDTO createDTO) {
        Post post = postService.createPost(createDTO.getUser_id(),createDTO.getContent(),createDTO.getInfo());
        return ResponseEntity.ok(post);
    }

    //Controller to like post.
    @PostMapping("/like")
    public ResponseEntity<Post> likePost(@RequestBody RequestDTO requestDTO)
    {
        Post post =postService.likePost(requestDTO.getUser_id(),requestDTO.getPost_id());
        return ResponseEntity.ok(post);
    }

    //Controller to delete post.
    @DeleteMapping("/delete")
    public ResponseEntity<Post> deletePost(@RequestBody RequestDTO requestDTO)
    {
        Post post = postService.deletePost(requestDTO.getUser_id(),requestDTO.getPost_id());
        return ResponseEntity.ok(post);
    }

    //Controller to get all the post.
    @GetMapping("/get-post")
    public ResponseEntity<List<Post>> getAllPost()
    {
        return ResponseEntity.ok(postService.getAllPost());
    }

    //Controller to get post by post_id
    @GetMapping("/getpost/{post_id}")
    public ResponseEntity<Post> getPost(@PathVariable String post_id)
    {
        return ResponseEntity.ok(postService.getPost(post_id));
    }

    //Controller to add comment on post.
    @PostMapping("/addcomment/{post_id}")
    public ResponseEntity<Post> addComment(@PathVariable String post_id , @RequestBody CommentDTO commentDto)
    {
        return ResponseEntity.ok(commentsService.addComment(post_id,commentDto));
    }

    //Controller to add replies on comments.
    @PostMapping("/addreplies/{postId}")
    public ResponseEntity<Post> addReply(@PathVariable String postId,@RequestBody ReplyDTO replyDTO)
    {
        return ResponseEntity.ok(commentsService.addReplies(postId, replyDTO.getComment_id(), replyDTO.getUser_id(), replyDTO.getText()));
    }

    //Controller to get all the comments on the post.
    @GetMapping("/getComments/{postId}")
    public ResponseEntity<List<Post>> getAllComments(@PathVariable String postId)
    {
        return ResponseEntity.ok(commentsService.getAllComments(postId));
    }




}
