package com.wescleyportfolio.java.todolist.repository;

import com.wescleyportfolio.java.todolist.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

     User findByUsername(String username);
}
