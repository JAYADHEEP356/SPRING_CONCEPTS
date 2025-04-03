package com.example.jwt_generation.Service;

import com.example.jwt_generation.Entity.User;
import com.example.jwt_generation.Repo.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserImp implements UserService{


    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder(12);

    public UserImp(UserRepo userRepo,AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
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

    public String verify(User user){
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

                if(authentication.isAuthenticated()){
                    return "the user is authenticated!...";
                }
        return "failed to authenticate";
    }


}
