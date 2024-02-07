package com.example.test.test.DTOs.Regd;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegdRes {

    private String userName;
    private String name;
    private String email;
    private String createdOn;
    private String updatedOn;
}
