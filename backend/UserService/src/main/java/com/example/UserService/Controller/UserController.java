package com.example.UserService.Controller;

import com.example.UserService.Model.Users;
import com.example.UserService.Service.FollowService;
import com.example.UserService.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController
{
    private final UserService userService;
    private final FollowService followService;
    public UserController(UserService userService, FollowService followService)
    {
        this.userService=userService;
        this.followService = followService;
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


    //Controller to follow user.
    @PostMapping("/{user_id}/follow/{target_id}")
    public ResponseEntity<String> follow(@PathVariable String user_id,@PathVariable String target_id)
    {
        followService.followUser(user_id,target_id);
        return ResponseEntity.ok("Follow Sucessfully");
    }

    //Controller to unfollow user.
    @PostMapping("/{user_id}/follow/{target_id}")
    public ResponseEntity<String> unfollow(@PathVariable String user_id,@PathVariable String target_id)
    {
        followService.unfollowUser(user_id,target_id);
        return ResponseEntity.ok("UnFollow Sucessfully");
    }

    //Controller to get follwer list.
    @GetMapping("/{user_id}/follwers")
    public ResponseEntity<Set<String>> getfollowers(@PathVariable String user_id)
    {
        return ResponseEntity.ok(followService.getFollower(user_id));
    }

    //Controller to get following list.
    @GetMapping("/{user_id}/follwers")
    public ResponseEntity<Set<String>> getfollowering(@PathVariable String user_id)
    {
        return ResponseEntity.ok(followService.getFollowering(user_id));
    }



}
