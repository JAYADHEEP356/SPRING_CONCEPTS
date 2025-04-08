package com.example.jwt_generation.Service;

import com.example.jwt_generation.Entity.User;
import com.example.jwt_generation.Repo.UserRepo;
import io.jsonwebtoken.Jwt;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserImp implements UserService{


    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder(12);

    public UserImp(UserRepo userRepo, AuthenticationManager authenticationManager,JwtService jwtService) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void Save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        userRepo.save(user);

    }

    @Override
    public void DeleteById(String username) {
        userRepo.deleteById(username);
    }

    @Override
    public User findByUsername(String username) {
       return userRepo.findUserByUsername(username);
    }

    public String verify(User user){
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

                if(authentication.isAuthenticated()){
                    return jwtService.generateToken(user.getUsername());
                }
        return "failed to authenticate";
    }


}
