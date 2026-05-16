package org.example.measurementtracker1.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.measurementtracker1.dto.auth.*;
import org.example.measurementtracker1.model.User;
import org.example.measurementtracker1.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("apiAuthController") @RequestMapping("/auth") @RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository; private final PasswordEncoder encoder;
    @PostMapping("/register") @ResponseStatus(HttpStatus.CREATED)
    public Map<String,Object> register(@Valid @RequestBody RegisterRequest request){
        if(userRepository.existsByUsername(request.getUsername())) throw new IllegalArgumentException("duplicate username");
        User u = new User(); u.setUsername(request.getUsername()); u.setPassword(encoder.encode(request.getPassword()));
        userRepository.save(u); return Map.of("id",u.getId(),"username",u.getUsername());
    }
    @PostMapping("/login") public Map<String,String> login(@Valid @RequestBody LoginRequest req){ return Map.of("message","Use HTTP Basic auth for protected endpoints"); }
}
