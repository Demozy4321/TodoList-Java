package com.example.test.test.repositories;

import com.example.test.test.entity.TodoItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoItemsRepo extends JpaRepository<TodoItems, Integer> {
}
