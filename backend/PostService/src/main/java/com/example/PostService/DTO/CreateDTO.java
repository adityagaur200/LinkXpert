package com.example.PostService.DTO;

import lombok.*;
import org.bson.types.Binary;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDTO
{
    private String user_id;
    private String info;
    private String  content;
}
