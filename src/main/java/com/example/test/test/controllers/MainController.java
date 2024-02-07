package com.example.test.test.controllers;

import com.example.test.test.entity.UserTable;
import com.example.test.test.security.UserPrincipal;
import com.example.test.test.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MainController {

    @Autowired
    private MainService mainService;

    @GetMapping("/hello")
    public String hello(@AuthenticationPrincipal UserPrincipal principal, @RequestHeader(value = "Authorization") String value) {
        System.out.println(value);
        System.out.println(principal);
        return "hello, this is secured";
    }

    @PostMapping("/addUser")
    private void addUser() {
         mainService.addUser();
    }

    @GetMapping("/findUsers")
    private List<UserTable> findUser() {
        return mainService.findUser();
    }

    @GetMapping("/findUser")
    private UserTable findUserByEmail(String email) {
        return mainService.findUserByEmail(email);
    }

}
