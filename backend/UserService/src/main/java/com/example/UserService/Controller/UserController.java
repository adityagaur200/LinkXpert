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


    //Controller to register user.
    @PostMapping("/register")
    public Users register(@RequestBody Users user)
    {
        return userService.registerUser(user);
    }

    //Controller to login user.
    @PostMapping("/login")
    public String login(@RequestBody Users user)
    {
        return userService.verify(user);
    }

    //Controller to get all users.
    @GetMapping("/all_user")
    public List<Users> getall()
    {
        return userService.getAll();
    }


    //Controller to get user by user_id.
    @GetMapping("/{user_id}")
    public Users getbyId(@PathVariable String user_id)
    {
        return userService.getUserById(user_id);
    }

}
