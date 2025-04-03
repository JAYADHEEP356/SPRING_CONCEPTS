package com.example.jwt_generation.Repo;

import com.example.jwt_generation.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,String>{

}
