package com.example.jwt_generation.Service;

import com.example.jwt_generation.Entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public void Save(User user);
    public void DeleteById(String username);


}
