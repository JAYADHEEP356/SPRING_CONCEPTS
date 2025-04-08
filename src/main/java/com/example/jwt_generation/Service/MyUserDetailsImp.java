package com.example.jwt_generation.Service;

import com.example.jwt_generation.Entity.User;
import com.example.jwt_generation.Entity.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsImp implements UserDetailsService {

    private final UserImp userService;

    public MyUserDetailsImp(UserImp userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findByUsername(username);
        if(user==null){

            throw new UsernameNotFoundException("the user was not found");

        }
        return new UserPrincipal(user);
    }
}
