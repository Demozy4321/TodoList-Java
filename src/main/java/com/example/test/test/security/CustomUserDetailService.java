package com.example.test.test.security;

import com.example.test.test.entity.UserTable;
import com.example.test.test.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private MainService mainService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserTable userTable = (UserTable) mainService.findUserByEmail(username);

        return new UserPrincipal(0L, userTable.getEmail(), userTable.getPassword(),userTable.getRoles().stream().map(role-> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList()));

    }
}
