package com.example.test.test.service;

import com.example.test.test.DTOs.Login.LoginReq;
import com.example.test.test.DTOs.Regd.RegdReq;
import com.example.test.test.DTOs.ResponseDTO;
import org.springframework.stereotype.Component;

@Component
public interface AuthService {
    ResponseDTO registerUser(RegdReq regdReq);

    ResponseDTO loginUser(LoginReq loginReq);
}
