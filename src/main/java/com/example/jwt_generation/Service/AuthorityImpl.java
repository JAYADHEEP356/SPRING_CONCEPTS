package com.example.jwt_generation.Service;

import com.example.jwt_generation.Entity.Authority;
import com.example.jwt_generation.Repo.AuthorityRepo;
import org.springframework.stereotype.Service;

@Service
public class AuthorityImpl implements AuthorityService{

    private final AuthorityRepo repo;

    public AuthorityImpl(AuthorityRepo repo) {
        this.repo = repo;
    }

    @Override
    public void Save(Authority authority) {
        repo.save(authority);
    }

    @Override
    public void DeleteById(int id) {
        repo.deleteById(id);
    }
}
