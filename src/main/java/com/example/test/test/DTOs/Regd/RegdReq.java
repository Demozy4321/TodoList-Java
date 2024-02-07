package com.example.test.test.DTOs.Regd;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegdReq {

    @NotBlank(message = "email cannot be blank")
    private String email;
    private String name;
    private String userName;
    private String password;

}
