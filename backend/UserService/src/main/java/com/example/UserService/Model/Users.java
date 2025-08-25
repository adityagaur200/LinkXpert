package com.example.UserService.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Users
{
    @Id
    private String user_id;
    private String username;
    private String gmail;
    private String password;
}
