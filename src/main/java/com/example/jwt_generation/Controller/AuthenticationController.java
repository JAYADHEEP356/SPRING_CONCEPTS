package com.example.jwt_generation.Controller;


import com.example.jwt_generation.Entity.Authority;
import com.example.jwt_generation.Entity.User;
import com.example.jwt_generation.Service.AuthorityImpl;
import com.example.jwt_generation.Service.UserImp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final UserImp userImp;
    private final AuthorityImpl authorityImp;

    public AuthenticationController(UserImp userImp,AuthorityImpl authorityImp) {
        this.userImp = userImp;
        this.authorityImp = authorityImp;
    }

    @PostMapping("/register")
    public String Register(@RequestBody User user){

          userImp.Save(user);
          Authority authority = new Authority();
          authority.setUsername(user);
          authority.setAuthority("ROLE_USER");
          authorityImp.Save(authority);
          return "the user was saved successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){

        return userImp.verify(user);
    }




}
