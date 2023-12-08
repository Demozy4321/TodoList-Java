package com.example.test.test.DTOs.TodoDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoItemDto {

    private String email;
    private String todoHeading;
    private String todoDesc;
    private String status;
    private Date createdOn;
    private Date updateOn;
    private Integer todoId;
}
