package com.example.demo.controller;

import com.example.demo.model.TodoDTO;
import com.example.demo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {

   @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos()
    {
        List<TodoDTO> todos = todoRepository.findAll();
        if (todos.size()>0)
        {
            return new ResponseEntity<List<TodoDTO>>(todos, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("No tasks available",HttpStatus.NOT_FOUND);
        }


    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo)
    {
        try
        {
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todo);
            return new ResponseEntity<TodoDTO>(todo,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id)
    {
        Optional<TodoDTO> todoOptional=todoRepository.findById(id);
        if (todoOptional.isPresent())
        {
            return new ResponseEntity<>(todoOptional.get(),HttpStatus.OK);

        }
        else {
            return new ResponseEntity<>("Todo not found with id"+id,HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updatedById(@PathVariable("id") String id,@RequestBody TodoDTO todo)
    {
        Optional<TodoDTO> todoOptional=todoRepository.findById(id);
        if (todoOptional.isPresent())
        {
            TodoDTO todoToSave=todoOptional.get();
            todoToSave.setCompleted(todo.getCompleted()!=null? todoToSave.getCompleted():todoToSave.getCompleted());
            todoToSave.setTodo(todo.getTodo()!=null? todo.getTodo():todoToSave.getTodo());
            todoToSave.setDesc(todo.getDesc()!=null? todo.getDesc():todoToSave.getDesc());
            todoToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todoToSave);
            return new ResponseEntity<>(todoToSave,HttpStatus.OK);

        }
        else {
            return new ResponseEntity<>("Todo not found with id"+id,HttpStatus.NOT_FOUND);
        }

    }


    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id )
    {
        try
        {
            todoRepository.deleteById(id);
            return new ResponseEntity<>("Successfully Deleted with id"+id,HttpStatus.OK);

        }catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }





}
