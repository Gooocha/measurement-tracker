package org.example.measurementtracker1.controller.api;

import lombok.RequiredArgsConstructor;
import org.example.measurementtracker1.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController("apiUserController") @RequestMapping("/users") @RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    @GetMapping("/me") public Map<String,Object> me(Principal principal){ var u=userRepository.findByUsername(principal.getName()).orElseThrow(); return Map.of("id",u.getId(),"username",u.getUsername());}
}
