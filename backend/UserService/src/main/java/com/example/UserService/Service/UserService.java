package com.example.UserService.Service;

import com.example.UserService.Model.Users;
import com.example.UserService.Repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(12);

    public UserService(UserRepository userRepo, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
    }

    // Register new user
    public Users registerUser(Users user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }

        if (userRepo.existsByGmail(user.getGmail())) {
            throw new RuntimeException("Email is already registered");
        }

        Users newUser = new Users();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcrypt.encode(user.getPassword())); // encode password
        newUser.setGmail(user.getGmail());

        return userRepo.save(newUser);
    }

    //login user function.
    public String verify(Users user) {
        Users foundUser = userRepo.findByUsername(user.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            if (auth.isAuthenticated()) {
                return "✅ Login successful for user: " + foundUser.getUsername();
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        return null;
    }
    //Get all user function.
    public List<Users> getAll()
    {
        return userRepo.findAll();
    }


    //Get user by user_id.
    public Users getUserById(String user_id)
    {
        return userRepo.findById(user_id).orElse(null);
    }
}
