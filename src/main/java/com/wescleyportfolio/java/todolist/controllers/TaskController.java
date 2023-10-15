package com.wescleyportfolio.java.todolist.controllers;

import com.wescleyportfolio.java.todolist.models.Task;
import com.wescleyportfolio.java.todolist.repository.TaskRepository;
import com.wescleyportfolio.java.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @GetMapping("/")
    public List list(HttpServletRequest request){
        List<Task> tasks =taskRepository.findByIdUser((UUID) request.getAttribute("idUser"));
        return tasks;
    }

    @PostMapping("/")
    public ResponseEntity createTask(@RequestBody Task task, HttpServletRequest request){
        task.setIdUser((UUID) request.getAttribute("idUser"));

        LocalDateTime currentDate = LocalDateTime.now();
        if(currentDate.isAfter(task.getStartAt()) || currentDate.isAfter(task.getEndAt())){
            return ResponseEntity.status(400).body("A data de inicio / data de término deve ser maior que a data atual "+currentDate.toLocalDate());
        }

        if(task.getStartAt().isAfter(task.getEndAt())){
            return ResponseEntity.status(400).body("A data de inicio deve ser menor que a data de termino");
        }
        Task createdTask = taskRepository.save(task);
        return ResponseEntity.status(201).body(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTask(@RequestBody Task task, @PathVariable UUID id, HttpServletRequest request){
        task.setIdUser((UUID) request.getAttribute("idUser"));

        Task existingTask = taskRepository.findById(id).orElse(null);

        if(existingTask == null){
            return ResponseEntity.badRequest().body("Tarefa não encontrada");
        }

        if(!existingTask.getIdUser().equals(request.getAttribute("idUser"))){
            return ResponseEntity.badRequest().body("O usuário não tem permissão para alterar essa tarefa");
        }

        Utils.copyNonNullProperties(task, existingTask);

        Task taskUpdated = taskRepository.save(existingTask);

        return ResponseEntity.ok().body(taskUpdated);
    }
}
