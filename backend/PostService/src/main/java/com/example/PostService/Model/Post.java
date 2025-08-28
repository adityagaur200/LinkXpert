package com.example.PostService.Model;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Document(collection = "Post")
@Getter
@Setter
@Data
public class Post
{
    @Id
    private String post_id;
    private String user_id;
    private String content;
    private String info;
    private Instant createdAt=Instant.now();
    private Integer Likes;
    private List<String> LikedBy;
    private List<Comment> comments = new ArrayList<>();
}
// This model is to store posts made by user and data related to it.


