package com.example.jwt_generation.Controller;


import com.example.jwt_generation.Entity.Authority;
import com.example.jwt_generation.Entity.User;
import com.example.jwt_generation.Service.AuthorityImpl;
import com.example.jwt_generation.Service.JwtService;
import com.example.jwt_generation.Service.UserImp;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final UserImp userImp;
    private final AuthorityImpl authorityImp;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationController(UserImp userImp, AuthorityImpl authorityImp, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userImp = userImp;
        this.authenticationManager = authenticationManager;
        this.authorityImp = authorityImp;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public String Register(@RequestBody User user){

          userImp.Save(user);
          Authority authority = new Authority();
          authority.setUsername(user.getUsername());
          authority.setAuthority("ROLE_USER");
          authorityImp.Save(authority);
          return "the user was saved successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

        if (authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }

        return "failed";
    }




}
