package com.example.PostService.Model;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.List;

@Document(collection = "Post")
@Getter
@Setter
@Data
public class Post
{
    @Id
    private String post_id;
    private Long user_id;
    private Binary content;
    private String info;
    private Instant createdAt=Instant.now();
    private Integer Likes;
    private List<String> LikedBy;
}

// This model is to store posts made by user and data related to it.


