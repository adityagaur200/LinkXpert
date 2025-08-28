package com.example.PostService.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CommentDTO
{
    private String user_id;
    private String text;

}
