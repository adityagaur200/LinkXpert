package com.example.PostService.DTO;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO
{
    private String post_id;
    private String user_id;
}
