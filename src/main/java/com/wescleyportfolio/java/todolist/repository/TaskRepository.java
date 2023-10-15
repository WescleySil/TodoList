package com.wescleyportfolio.java.todolist.repository;

import com.wescleyportfolio.java.todolist.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByIdUser(UUID idUser);

}
