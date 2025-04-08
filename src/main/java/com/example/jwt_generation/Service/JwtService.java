package com.example.jwt_generation.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
public class JwtService {


    private String key = "";

    public JwtService() {

        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey();
            key = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String username){

         Map<String,Object> map = new HashMap<>(); //for custom claims

         return Jwts.builder()
                 .claims()
                 .add(map)
                 .subject(username)
                 .issuedAt(new Date(System.currentTimeMillis()))
                 .expiration(new Date(System.currentTimeMillis()+60*60*30))
                 .and()
                 .signWith(getKey())
                 .compact();

    }

    private Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getAllClaims(String token){

        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }



    public String getUsername(String token) {

        return extractClaim(token,Claims::getSubject);
    }

    private <T> T extractClaim(String token,Function<Claims,T> claimsTFunction){

              Claims claims = getAllClaims(token);
              return claimsTFunction.apply(claims);
    }

    private Date extractExpiration(String token){

        return extractClaim(token,Claims::getExpiration);
    }

    private boolean isExpired(String token){

        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token) {

        String username = getUsername(token);

        if(username.equals()){


        }

    }
}
