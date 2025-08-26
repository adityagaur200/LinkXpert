package com.example.UserService.Repository;

import com.example.UserService.Model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<Users,String>
{
    Optional<Users> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByGmail(String gmail);

}
