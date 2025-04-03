package com.example.jwt_generation.Service;

import com.example.jwt_generation.Entity.Authority;
import com.example.jwt_generation.Entity.User;

public interface AuthorityService {



    public void Save(Authority authority);
    public void DeleteById(int id);
}
