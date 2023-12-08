package com.example.test.test.service;

import com.example.test.test.DTOs.ResponseDTO;
import com.example.test.test.DTOs.TodoDtos.TodoEditDto;
import com.example.test.test.DTOs.TodoDtos.TodoEntryDto;
import org.springframework.stereotype.Component;

@Component
public interface TodoService {
    ResponseDTO getAllTodos(String email);

    ResponseDTO addTodo(TodoEntryDto todoEntryDto);

    ResponseDTO deleteTodo(Integer itemId);

    ResponseDTO editTodo(TodoEditDto todoEditDto);

    ResponseDTO getTodoItem(Integer itemId);

    ResponseDTO completeTodo(Integer itemId);
}
