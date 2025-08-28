package com.example.PostService.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Setter
@Getter
public class Comment{
    private String comment_id = UUID.randomUUID().toString();
    private String user_id;
    private String text;
    private List<Comment> replies = new ArrayList<>();
    private Instant createdAt=Instant.now();
}

