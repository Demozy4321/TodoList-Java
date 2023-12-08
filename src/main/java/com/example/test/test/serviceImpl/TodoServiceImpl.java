package com.example.test.test.serviceImpl;

import com.example.test.test.DTOs.ResponseDTO;
import com.example.test.test.DTOs.TodoDtos.TodoEditDto;
import com.example.test.test.DTOs.TodoDtos.TodoEntryDto;
import com.example.test.test.DTOs.TodoDtos.TodoItemDto;
import com.example.test.test.entity.TodoItems;
import com.example.test.test.entity.TodoStatus;
import com.example.test.test.entity.UserTable;
import com.example.test.test.repositories.TodoItemsRepo;
import com.example.test.test.repositories.UserRepo;
import com.example.test.test.service.TodoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TodoItemsRepo todoItemsRepo;

    @Override
    public ResponseDTO getAllTodos(String email) {
        ResponseDTO responseDTO = new ResponseDTO();

        try {

            List<TodoItemDto> result = new ArrayList<>();
            UserTable user = userRepo.findUserByEmail(email);

            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<TodoItems> todoItemsCriteriaQuery = criteriaBuilder.createQuery(TodoItems.class);
            Root<TodoItems> todoItemsRoot = todoItemsCriteriaQuery.from(TodoItems.class);
            List<Predicate> todoPredicate = new ArrayList<>();
            todoPredicate.add(criteriaBuilder.equal(todoItemsRoot.get("user"),user));
            todoPredicate.add(criteriaBuilder.equal(todoItemsRoot.get("todoStatus").get("status"),"Active"));

            todoItemsCriteriaQuery.select(todoItemsRoot).where(todoPredicate.toArray(new Predicate[]{}));
            todoItemsCriteriaQuery.orderBy(criteriaBuilder.desc(todoItemsRoot.get("updatedOn")));

            Query todoItemsQuery = entityManager.createQuery(todoItemsCriteriaQuery);
            List<TodoItems> todoItems = todoItemsQuery.getResultList();

            for (TodoItems todoItems1 : todoItems)
            {
                TodoItemDto todoItemDto = new TodoItemDto();
                todoItemDto.setEmail(todoItems1.getUser().getEmail());
                todoItemDto.setStatus(todoItems1.getTodoStatus().getStatus());
                todoItemDto.setTodoHeading(todoItems1.getTodoHeading());
                todoItemDto.setTodoDesc(todoItems1.getTodoDesc());
                todoItemDto.setCreatedOn(todoItems1.getCreatedOn());
                todoItemDto.setUpdateOn(todoItems1.getUpdatedOn());
                todoItemDto.setTodoId(todoItems1.getId());

                result.add(todoItemDto);
            }


            responseDTO.setData(result);
            responseDTO.setStatus(true);
            responseDTO.setMessage("Data Fetched Successfully");

        }catch (Exception e) {
            e.printStackTrace();
            responseDTO.setData(new ArrayList<>());
            responseDTO.setStatus(false);
            responseDTO.setMessage("Exception Occured");
        }finally {
            return responseDTO;
        }
    }

    @Override
    public ResponseDTO addTodo(TodoEntryDto todoEntryDto) {
        ResponseDTO responseDTO = new ResponseDTO();

        TodoStatus todoStatus = entityManager.find(TodoStatus.class,1);
        UserTable user = entityManager.find(UserTable.class, todoEntryDto.getEmail());

        if (user == null || todoStatus == null)
        {
            responseDTO.setData(new ArrayList<>());
            responseDTO.setStatus(false);
            responseDTO.setMessage("status and user cannot be null");
            return responseDTO;
        }

        TodoItems todoItem = new TodoItems();
        todoItem.setTodoDesc(todoEntryDto.getTodoDesc());
        todoItem.setTodoHeading(todoEntryDto.getTodoHeading());
        todoItem.setTodoStatus(todoStatus);
        todoItem.setUser(user);

        entityManager.persist(todoItem);

        responseDTO.setMessage("Todo Added Successfully");
        responseDTO.setStatus(true);
        responseDTO.setData(todoItem);

        return responseDTO;

    }

    @Override
    public ResponseDTO deleteTodo(Integer itemId) {

        ResponseDTO responseDTO = new ResponseDTO();

        TodoItems todoItem = todoItemsRepo.findById(itemId).orElse(null);

        if (todoItem == null)
        {
            responseDTO.setMessage("Todo Does not exist");
            responseDTO.setStatus(false);
            responseDTO.setData(new ArrayList<>());
            return responseDTO;
        }

        todoItemsRepo.delete(todoItem);

        responseDTO.setMessage("Todo Deleted Successfully");
        responseDTO.setStatus(true);
        responseDTO.setData(todoItem);

        return responseDTO;
    }

    @Override
    public ResponseDTO editTodo(TodoEditDto todoEditDto) {
        ResponseDTO responseDTO = new ResponseDTO();

        TodoItems todoItem = todoItemsRepo.findById(todoEditDto.getItemId()).orElse(null);

        if (todoItem == null)
        {
            responseDTO.setMessage("Todo Does not exist");
            responseDTO.setStatus(false);
            responseDTO.setData(new ArrayList<>());
            return responseDTO;
        }

        todoItem.setTodoHeading(todoEditDto.getTodoHeading());
        todoItem.setTodoDesc(todoEditDto.getTodoDesc());

        entityManager.merge(todoItem);

        responseDTO.setMessage("Todo Deleted Successfully");
        responseDTO.setStatus(true);
        responseDTO.setData(todoItem);

        return responseDTO;
    }

    @Override
    public ResponseDTO getTodoItem(Integer itemId) {
        ResponseDTO responseDTO = new ResponseDTO();
        TodoItems todoItem = entityManager.find(TodoItems.class, itemId);

        if (todoItem == null)
        {
            responseDTO.setMessage("Todo Does not exist");
            responseDTO.setStatus(false);
            responseDTO.setData(new ArrayList<>());
            return responseDTO;
        }

        responseDTO.setData(todoItem);
        responseDTO.setMessage("Todo Fetched Successfully");
        responseDTO.setStatus(true);

        return responseDTO;
    }

    @Override
    public ResponseDTO completeTodo(Integer itemId) {
        ResponseDTO responseDTO = new ResponseDTO();
        TodoItems todoItem = entityManager.find(TodoItems.class, itemId);

        if (todoItem == null)
        {
            responseDTO.setMessage("Todo Does not exist");
            responseDTO.setStatus(false);
            responseDTO.setData(new ArrayList<>());
            return responseDTO;
        }

        TodoStatus completeStatus = entityManager.find(TodoStatus.class,2);
        if (completeStatus == null)
        {
            responseDTO.setMessage("Todo Status Does not exist");
            responseDTO.setStatus(false);
            responseDTO.setData(new ArrayList<>());
            return responseDTO;
        }

        todoItem.setTodoStatus(completeStatus);

        entityManager.merge(todoItem);

        responseDTO.setData(todoItem);
        responseDTO.setMessage("Todo Fetched Successfully");
        responseDTO.setStatus(true);

        return responseDTO;
    }
}
