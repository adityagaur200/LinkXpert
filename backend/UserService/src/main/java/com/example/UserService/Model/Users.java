package com.example.UserService.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

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
    public Set<String> followers=new HashSet<>();
    public Set<String> following = new HashSet<>();
}
