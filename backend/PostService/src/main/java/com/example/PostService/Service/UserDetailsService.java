package com.example.PostService.Service;
import com.example.PostService.DTO.UserDetailsDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class UserDetailsService {

    private final WebClient webClient;

    //Inject WebClient bean, not WebClientConfig
    public UserDetailsService(WebClient webClient) {
        this.webClient = webClient;
    }

    public UserDetailsDTO getUserById(Long userId) {
        return webClient.get()
                .uri("/{id}", userId)   // -> http://localhost:8090/api/users/{id}
                .retrieve()
                .bodyToMono(UserDetailsDTO.class)
                .block(); // sync call (ok for internal service-to-service)
    }
}
