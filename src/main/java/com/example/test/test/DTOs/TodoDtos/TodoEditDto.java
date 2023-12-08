package com.example.test.test.DTOs.TodoDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoEditDto {

    private String email;
    private String todoHeading;
    private String todoDesc;
    private Integer itemId;
}
