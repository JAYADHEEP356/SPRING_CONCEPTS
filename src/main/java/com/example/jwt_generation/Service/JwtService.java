package com.example.jwt_generation.Service;


import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    public String generateToken(String username){

         Map<String,Object> map = new HashMap<>(); //for custom claims

         return Jwts.builder()
                 .claims()
                 .add(map)
                 .subject(username)
                 .issuedAt(new Date(System.currentTimeMillis()))
                 .expiration(new Date(System.currentTimeMillis()*60*60*30))
                 .and()
                 .signWith(getkey())
                 .compact();

    }


}
