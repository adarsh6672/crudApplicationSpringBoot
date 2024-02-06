package com.crudbackend.userCrudApp.repository;

import com.crudbackend.userCrudApp.model.Image;
import com.crudbackend.userCrudApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRespository extends JpaRepository<Image, Integer > {

    Optional<Image> findByUser(User user);
}
