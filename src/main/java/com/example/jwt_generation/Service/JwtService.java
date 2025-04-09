package com.example.jwt_generation.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {


    @Value("${bezkoder.app.jwtSecret}")
    private  String secretKey;


    public String generateSecretKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = keyGen.generateKey();
            System.out.println("Secret Key : " + secretKey.toString());
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating secret key", e);
        }
    }


    public String generateToken(String username){

         Map<String,Object> map = new HashMap<>(); //for custom claims

         return Jwts.builder()
                 .claims()
                 .add(map)
                 .subject(username)
                 .issuedAt(new Date(System.currentTimeMillis()))
                 .expiration(new Date(System.currentTimeMillis()+1000*60*30))
                 .and()
                 .signWith(getKey())
                 .compact();

    }

    private Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(getKey())
                .build().parseSignedClaims(token).getPayload();
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

    public boolean validateToken(String token, UserDetails userDetails) {

        String username = getUsername(token);
        System.out.println(username);
        System.out.println(userDetails.getUsername());

        boolean one = username.equals(userDetails.getUsername());
        boolean two = isExpired(token);

        System.out.println("one "+one+" two "+two);

        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }
}
