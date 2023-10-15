package com.wescleyportfolio.java.todolist.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="tb_users")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;


    @Column(unique = true)
    private String username;

    private String name;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

}