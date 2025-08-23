package com.example.PostService.Service;
import com.example.PostService.DTO.UserDetailsDTO;
import com.example.PostService.Model.Post;
import com.example.PostService.Repository.PostRepository;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.ArrayList;


@Service
public class PostService
{
    private final PostRepository PostRepo;
    private final UserDetailsService userDetailsService;

    //Injecting Dependencies
    public PostService(PostRepository postRepository,UserDetailsService userDetailsService)
    {
        this.userDetailsService=userDetailsService;
        this.PostRepo=postRepository;
    }

    //Creating service function (createPost) to create post.
    public Post createPost(Long user_id, Binary content)
    {
        // Validate if user exists or not.
        UserDetailsDTO user= userDetailsService.getUserById(user_id);
        if(user==null)
        {
            throw new RuntimeException("User not found with ID: "+user_id);
        }
        Post post = new Post();
        post.setCreatedAt(Instant.now());
        post.setContent(content);
        post.setLikedBy(new ArrayList<>());
        post.setLikes(0);
        post.setUser_id(user_id);

        return PostRepo.save(post);
    }

    //Service function (likePost) for like and unlike and add and remove liked users.
    public Post likePost(Long user_id, String post_id)
    {
        UserDetailsDTO user= userDetailsService.getUserById(user_id);
        if(user==null)
        {
            throw new RuntimeException("User not found with ID: "+user_id);
        }

        Post post =PostRepo.findById(post_id).orElseThrow(()->new RuntimeException("Post not found with Id: "+post_id));
        if(post.getLikedBy()==null)
        {
            post.setLikedBy(new ArrayList<>());
        }
        if(!post.getLikedBy().contains(user_id))
        {
            post.getLikedBy().add(String.valueOf(user_id));
            post.setLikes(post.getLikes()+1);
        }
        else {
            post.getLikedBy().remove(user_id);
            post.setLikes(post.getLikes()-1);
        }
        return PostRepo.save(post);
    }
}

