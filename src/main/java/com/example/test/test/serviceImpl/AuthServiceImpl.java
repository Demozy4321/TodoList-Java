package com.example.test.test.serviceImpl;

import com.example.test.test.DTOs.Login.LoginReq;
import com.example.test.test.DTOs.Login.LoginRes;
import com.example.test.test.DTOs.Login.LoginResV2;
import com.example.test.test.DTOs.Regd.RegdReq;
import com.example.test.test.DTOs.Regd.RegdRes;
import com.example.test.test.DTOs.ResponseDTO;
import com.example.test.test.entity.UserRole;
import com.example.test.test.entity.UserTable;
import com.example.test.test.repositories.UserRepo;
import com.example.test.test.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Transactional(rollbackFor = Exception.class)
@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseDTO registerUser(RegdReq regdReq) {

        ResponseDTO responseDTO = new ResponseDTO();

        try {

            UserTable user = userRepo.findUserByEmail(regdReq.getEmail());

            if (user != null) {
                log.error("User already exists");
                responseDTO.setMessage("User Already exits with this email");
                responseDTO.setStatus(false);
                responseDTO.setData(new ArrayList<>());
                return responseDTO;
            }

            Set<UserRole> roleSet = new HashSet<>();
            roleSet.add(UserRole.ROLE_USER);

            user = new UserTable(
                    regdReq.getEmail(),
                    regdReq.getName(),
                    regdReq.getUserName(),
                    new Date(),
                    new Date(),
                    passwordEncoder.encode(regdReq.getPassword()),
                    roleSet
            );

            userRepo.save(user);

            RegdRes regdRes = new RegdRes(
                    user.getUserName(),
                    user.getName(),
                    user.getEmail(),
                    user.getCreatedOn().toString(),
                    user.getUpdatedOn().toString()
            );

            responseDTO.setStatus(true);
            responseDTO.setData(regdRes);
            responseDTO.setMessage("User Registered Successfully");

            log.info("User Registered Successfully");


        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception Occured: " + e);
            responseDTO.setData(new ArrayList<>());
            responseDTO.setMessage("Exception Occured in registering user");
            responseDTO.setStatus(false);

        } finally {
            return responseDTO;
        }

    }

    @Override
    public ResponseDTO loginUser(LoginReq loginReq) {

        ResponseDTO responseDTO = new ResponseDTO();

        try {

            UserTable userTable = userRepo.findUserByEmail(loginReq.getEmail());

            if (userTable == null) {
                log.error("Wrong Credentials or User Doesnot Exists");
                responseDTO.setMessage("User Does not exits");
                responseDTO.setData(new ArrayList<>());
                responseDTO.setStatus(false);
                return responseDTO;
            }


            if (!passwordEncoder.matches(loginReq.getPassword(), userTable.getPassword())) {
                log.error("Wrong Credentials or User Doesnot Exists");
                responseDTO.setMessage("Wrong Credentials or User Doesnot Exists");
                responseDTO.setData(new ArrayList<>());
                responseDTO.setStatus(false);
                return responseDTO;
            }

            LoginResV2 loginRes = new LoginResV2(
                    userTable.getEmail(),
                    userTable.getName(),
                    userTable.getUserName(),
                    userTable.getCreatedOn(),
                    userTable.getUpdatedOn()
            );

            responseDTO.setStatus(true);
            responseDTO.setData(loginRes);
            responseDTO.setMessage("User Logged In Successfully");

            log.info("User Logged In Successfully");

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception Occured while login: " + e);
            responseDTO.setData(new ArrayList<>());
            responseDTO.setMessage("Exception Occured in logging in user");
            responseDTO.setStatus(false);
        } finally {
            return responseDTO;
        }
    }
}
