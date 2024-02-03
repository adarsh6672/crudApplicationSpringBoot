package com.crudbackend.userCrudApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public ResponseEntity<String> demo(){
        return ResponseEntity.ok("hello it is from secured url");
    }

    @GetMapping("/admin_dashboard")
    public ResponseEntity<String> adminDashboard(){
        return ResponseEntity.ok("logged in as admin");
    }

}
