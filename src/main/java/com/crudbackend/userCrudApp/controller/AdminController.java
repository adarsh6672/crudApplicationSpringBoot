package com.crudbackend.userCrudApp.controller;

import com.crudbackend.userCrudApp.DTO.UserUpdateDTO;
import com.crudbackend.userCrudApp.model.User;
import com.crudbackend.userCrudApp.repository.UserRepository;
import com.crudbackend.userCrudApp.service.AdminService;
import com.crudbackend.userCrudApp.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AdminController {

    private JwtService jwtService;
    private UserRepository userRepository;

    private AdminService adminService;

    public AdminController(JwtService jwtService, UserRepository userRepository, AdminService adminService) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.adminService = adminService;
    }


    @GetMapping("/admin/fetchusers")
    public ResponseEntity<?> adminDashboard(){

        List<User> users=adminService.findAllData();
        return ResponseEntity.ok(users);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/admin/deleteuser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        boolean isDeleted=adminService.deleteUser(id);
        if(isDeleted) {
            return ResponseEntity.ok().body("deleted");
        }else {
            return ResponseEntity.ok("item not found");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/admin/adduser")
    public ResponseEntity<?> addUser(@RequestBody User request){
        boolean res=adminService.register(request);
        if(res){
            return ResponseEntity.ok("user added");
        }else {
            return ResponseEntity.internalServerError().build();
        }

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/admin/updateuser")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO data){
        User updatedUser= adminService.updateUser(data);
        if(updatedUser==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }



}
