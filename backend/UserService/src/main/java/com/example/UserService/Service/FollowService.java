package com.example.UserService.Service;

import com.example.UserService.Model.Users;
import com.example.UserService.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class FollowService
{
    private final UserRepository userRepository;

    public FollowService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    //Service function to add follower.
    public void followUser(String user_id,String target_id)
    {
        Users loginUser = userRepository.findById(user_id).orElse(null);
        Users followUser = userRepository.findById(target_id).orElse(null);
        loginUser.getFollowing().add(target_id);
        followUser.getFollowing().add(user_id);
        userRepository.save(loginUser);
        userRepository.save(followUser);
    }


    //Service function to unfollow user.
    public void unfollowUser(String user_id,String target_id)
    {
        Users loginUser = userRepository.findById(user_id).orElse(null);
        Users followUser = userRepository.findById(target_id).orElse(null);
        loginUser.getFollowing().remove(target_id);
        followUser.getFollowing().remove(user_id);
        userRepository.save(loginUser);
        userRepository.save(followUser);
    }


    //Service function to get follower list.
    public Set<String> getFollower(String user_id)
    {
       Users activeUser=userRepository.findById(user_id).orElse(null);
       if(activeUser == null) {
           return Collections.singleton("User Not Found");
       }
       return activeUser.getFollowers();
    }


    //Service function to get following list.
    public Set<String> getFollowering(String user_id)
    {
        Users activeUser=userRepository.findById(user_id).orElse(null);
        if(activeUser == null) {
            return Collections.singleton("User Not Found");
        }
        return activeUser.getFollowing();
    }
}
