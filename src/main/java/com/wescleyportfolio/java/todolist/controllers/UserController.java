package com.wescleyportfolio.java.todolist.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.wescleyportfolio.java.todolist.models.User;
import com.wescleyportfolio.java.todolist.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping()
    public ResponseEntity createUser(@RequestBody User user){
        User existingUser = userRepository.findByUsername(user.getUsername());
        if(existingUser != null){
            System.out.println("Usuário ja existe");
            return ResponseEntity.status(400).body("Usuário já existe");
        }

        String hasredPassword = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());

        user.setPassword(hasredPassword);

        User createdUSer = userRepository.save(user);
        return ResponseEntity.status(201).body(createdUSer);
    }
}
