package com.example.test.test.DTOs.Login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResV2 {

    private String email;
    private String fullName;
    private String userName;
    private Date createdOn;
    private Date updatedOn;

}
