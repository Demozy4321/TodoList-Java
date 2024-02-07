package com.example.test.test.controllers;

import com.example.test.test.DTOs.Login.LoginReq;
import com.example.test.test.DTOs.Login.LoginRes;
import com.example.test.test.DTOs.Regd.RegdReq;
import com.example.test.test.DTOs.ResponseDTO;
import com.example.test.test.security.JwtIssuer;
import com.example.test.test.security.UserPrincipal;
import com.example.test.test.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtIssuer jwtIssuer;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public LoginRes login(@RequestBody @Validated LoginReq loginReq)
    {
        var authentication= authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal) authentication.getPrincipal();

        var roles = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        String token = jwtIssuer.issue(principal.getUserId(),principal.getEmail(),roles);

        return new LoginRes(token);
    }

    @PostMapping("/userLogin")
    public ResponseEntity<ResponseDTO> userLogin(@RequestBody @Validated LoginReq loginReq)
    {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            responseDTO = authService.loginUser(loginReq);
        }catch (Exception e) {
            e.printStackTrace();
            responseDTO.setMessage("Exception Occured: " + e);
            responseDTO.setStatus(false);
            responseDTO.setData(new ArrayList<>());
        }finally {
            return ResponseEntity.of(Optional.of(responseDTO));
        }
    }

    @PostMapping("/userRegistration")
    public ResponseEntity<ResponseDTO> Registration(@RequestBody @Validated RegdReq regdReq)
    {
        ResponseDTO responseDTO = new ResponseDTO();

        try {
            responseDTO = authService.registerUser(regdReq);
        }catch (Exception e) {
            e.printStackTrace();
            responseDTO.setMessage("Exception Occured: " + e);
            responseDTO.setStatus(false);
            responseDTO.setData(new ArrayList<>());
        }finally {
            return ResponseEntity.of(Optional.of(responseDTO));
        }
    }
}
