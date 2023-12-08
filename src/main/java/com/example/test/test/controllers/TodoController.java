package com.example.test.test.controllers;

import com.example.test.test.DTOs.ResponseDTO;
import com.example.test.test.DTOs.TodoDtos.TodoEditDto;
import com.example.test.test.DTOs.TodoDtos.TodoEntryDto;
import com.example.test.test.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/todo")
@CrossOrigin(origins = "*")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/allTodos")
    public ResponseEntity<ResponseDTO> getTodos(@RequestParam String email)
    {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO = todoService.getAllTodos(email);
        }catch (Exception e) {

        }finally {
            return ResponseEntity.of(Optional.of(responseDTO));
        }
    }

    @PostMapping("/addTodo")
    public ResponseEntity<ResponseDTO> addTodo(@RequestBody @Validated TodoEntryDto todoEntryDto)
    {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO = todoService.addTodo(todoEntryDto);
        }catch (Exception e) {

        }finally {
            return ResponseEntity.of(Optional.of(responseDTO));
        }
    }

    @PostMapping("/deleteTodo")
    public ResponseEntity<ResponseDTO> deleteTodo(@RequestParam Integer itemId)
    {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO = todoService.deleteTodo(itemId);
        }catch (Exception e) {

        }finally {
            return ResponseEntity.of(Optional.of(responseDTO));
        }
    }

    @PostMapping("/editTodo")
    public ResponseEntity<ResponseDTO> editTodo(@RequestBody @Validated TodoEditDto todoEditDto)
    {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO = todoService.editTodo(todoEditDto);
        }catch (Exception e) {

        }finally {
            return ResponseEntity.of(Optional.of(responseDTO));
        }
    }

    @GetMapping("/getTodoItem")
    public ResponseEntity<ResponseDTO> getTodoItem(@RequestParam Integer itemId)
    {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO = todoService.getTodoItem(itemId);
        }catch (Exception e) {

        }finally {
            return ResponseEntity.of(Optional.of(responseDTO));
        }
    }

    @PostMapping("/completeTodo")
    public ResponseEntity<ResponseDTO> completeTodo(@RequestParam Integer itemId)
    {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            responseDTO = todoService.completeTodo(itemId);
        }catch (Exception e) {

        }finally {
            return ResponseEntity.of(Optional.of(responseDTO));
        }
    }
}
