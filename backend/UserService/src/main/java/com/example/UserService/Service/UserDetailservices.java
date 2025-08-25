package com.example.UserService.Service;

import com.example.UserService.Model.UserPrincipal;
import com.example.UserService.Model.Users;
import com.example.UserService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailservices implements UserDetailsService
{
    @Autowired
    private final UserRepository userRepo;

    public UserDetailservices(UserRepository userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user =userRepo.findByUsername(username);
        if(user.isEmpty())
        {
            throw new UsernameNotFoundException("user not found");
        }
        return new UserPrincipal(user.get());
    }
}
