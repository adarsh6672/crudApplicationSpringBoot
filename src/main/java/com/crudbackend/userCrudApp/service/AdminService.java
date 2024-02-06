package com.crudbackend.userCrudApp.service;

import com.crudbackend.userCrudApp.DTO.UserUpdateDTO;
import com.crudbackend.userCrudApp.model.AuthenticationResponse;
import com.crudbackend.userCrudApp.model.User;
import com.crudbackend.userCrudApp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AdminService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAllData(){
        return  userRepository.findAll();
    }


    public boolean deleteUser(Integer id){
        userRepository.deleteById(id);
        return  true;
    }

    public boolean register(User request){
        User user= new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(request.getRole());

        userRepository.save(user);



        return true;
    }

    public User updateUser(UserUpdateDTO userUpdateDTO){
        User user = userRepository.findById(userUpdateDTO.getId()).orElseThrow();

        user.setUsername(userUpdateDTO.getUsername());
        user.setFirstName(userUpdateDTO.getFirstName());
        user.setLastName(userUpdateDTO.getLastName());
        user.setRole(userUpdateDTO.getRole());

        return  userRepository.save(user);
    }


}
