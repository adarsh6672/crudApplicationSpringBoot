package com.crudbackend.userCrudApp.controller;

import com.crudbackend.userCrudApp.model.User;
import com.crudbackend.userCrudApp.repository.UserRepository;
import com.crudbackend.userCrudApp.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    private JwtService jwtService;
    private UserRepository userRepository;

    public ProfileController(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> demo(@RequestHeader("Authorization") String headerToken){
        String token = headerToken.substring(7);
        String username = jwtService.extractUsername(token);
        User user=userRepository.findByUsername(username).orElseThrow();


        return ResponseEntity.ok(user);
    }






}
