package com.example.PostService.Service;
import com.example.PostService.DTO.CommentDTO;
import com.example.PostService.Model.Comment;
import com.example.PostService.Model.Post;
import com.example.PostService.Repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentsService
{
    private final PostRepository repo;

    public CommentsService(PostRepository repo)
    {
        this.repo=repo;
    }

    //This function is to add comment on the post.
    public Post addComment(String post_id,CommentDTO commentDTO)
    {
        Post post =repo.findById(post_id).orElseThrow(()->new RuntimeException("Post not Found"));

            Comment comment = new Comment();
            comment.setUser_id(comment.getUser_id());
            comment.setText(comment.getText());

            post.getComments().add(comment);
            return repo.save(post);

    }

    //This function is to delete comment.
    public Post deleteComment(String postId, String commentId) {
        Post post = repo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // remove the comment by filtering
        post.setComments(
                post.getComments()
                        .stream()
                        .filter(c -> !c.getComment_id().equals(commentId))
                        .collect(Collectors.toList())
        );

        return repo.save(post);  // save updated post
    }

    //This function add replies to comments.
    public Post addReplies(String postId, String CommentId, String userId, String text)
    {
        Post post = repo.findById(postId).orElseThrow(()->new RuntimeException("Post not found"));
        Comment reply = new Comment();
        reply.setUser_id(userId);
        reply.setText(text);
        addReply(post.getComments(),CommentId,reply);
        return repo.save(post);
    }

    //Helper function to add nested replies to comment.
    public boolean addReply(List<Comment> comments, String commentId, Comment text)
    {
        for(Comment comment :comments)
        {
            if(comment.getComment_id().equals(commentId))
            {
                comment.getReplies().add(text);
                return true;
            }
            if(addReply(comment.getReplies(),commentId,text))
            {
                return true;
            }
        }
        return false;
    }

    //This function return all the comments on the post by post_id.
    public List<Post> getAllComments(String post_id)
    {
        return Collections.singletonList(repo.findById(post_id).orElse(null));
    }


}
