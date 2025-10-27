package com.meetly.meetly.controller;

import com.meetly.meetly.model.User;
import com.meetly.meetly.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Allow frontend from any origin

public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public Map<String, String> signup(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            response.put("status", "error");
            response.put("message", "Email already registered");
            return response;
        }

        userRepository.save(user);
        response.put("status", "success");
        response.put("message", "Account created successfully");
        response.put("fullName", user.getFullName());
        return response;
    }

    @PostMapping("/signin")
    public Map<String, String> signin(@RequestBody Map<String, String> loginData) {
        Map<String, String> response = new HashMap<>();

        String email = loginData.get("email");
        String password = loginData.get("password");

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null || !user.getPassword().equals(password)) {
            response.put("status", "error");
            response.put("message", "Invalid email or password");
            return response;
        }

        response.put("status", "success");
        response.put("message", "Logged in successfully");
        response.put("fullName", user.getFullName());
        return response;
    }
}
