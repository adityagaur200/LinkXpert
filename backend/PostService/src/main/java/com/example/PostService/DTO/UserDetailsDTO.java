package com.example.PostService.DTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserDetailsDTO
{
    private String user_id;
    private String username;
    private String email;
}
// Created User-Detail DTO to get/map user details from the userDb from the userservice.
