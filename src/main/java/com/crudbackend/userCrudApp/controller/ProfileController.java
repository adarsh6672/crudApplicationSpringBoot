package com.crudbackend.userCrudApp.controller;

import com.crudbackend.userCrudApp.model.Image;
import com.crudbackend.userCrudApp.model.User;
import com.crudbackend.userCrudApp.repository.ImageRespository;
import com.crudbackend.userCrudApp.repository.UserRepository;
import com.crudbackend.userCrudApp.service.JwtService;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
public class ProfileController {

    private JwtService jwtService;
    private UserRepository userRepository;

    private ImageRespository imageRespository;

    public ProfileController(JwtService jwtService, UserRepository userRepository, ImageRespository imageRespository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.imageRespository = imageRespository;
    }

    private static final String UPLOAD_PATH="/home/adarsh/BROTOTYPE/CRUD_APP_BACKEND/userCrudApp/src/main/resources/static/profilePic";

    @GetMapping("/profile")
    public ResponseEntity<?> demo(@RequestHeader("Authorization") String headerToken){
        String token = headerToken.substring(7);
        String username = jwtService.extractUsername(token);
        User user=userRepository.findByUsername(username).orElseThrow();


        return ResponseEntity.ok(user);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/user/uploadimage")
    public  ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file,
                                          @RequestHeader("Authorization") String headerToken){
        String fileName= file.getOriginalFilename();
        System.out.println(fileName);
        String token = headerToken.substring(7);
        String username = jwtService.extractUsername(token);
        User user=userRepository.findByUsername(username).orElseThrow();

        try{
            file.transferTo(new File(UPLOAD_PATH+fileName));
            Image image= new Image();
            image.setUser(user);
            image.setImagePath(UPLOAD_PATH+fileName);
            imageRespository.save(image);
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/profile/getimage")
    public ResponseEntity<?> getImage(@RequestHeader("Authorization") String headerToken) throws IOException {
        String token = headerToken.substring(7);
        String username = jwtService.extractUsername(token);
        User user=userRepository.findByUsername(username).orElseThrow();
        Image image=imageRespository.findByUser(user).orElseThrow();
        ClassPathResource imageFile = new ClassPathResource(image.getImagePath());

        byte[] imageBytes = StreamUtils.copyToByteArray(imageFile.getInputStream());

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);

    }







}
