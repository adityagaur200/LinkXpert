package com.example.UserService.Controller;

import com.example.UserService.Model.Users;
import com.example.UserService.Service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController
{
    private final UserService userService;
    public UserController(UserService userService)
    {
        this.userService=userService;
    }

    @PostMapping("/register")
    public Users register(@RequestBody Users user)
    {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user)
    {
        return userService.verify(user);
    }

    @GetMapping("/all_user")
    public List<Users> getall()
    {
        return userService.getAll();
    }

}
