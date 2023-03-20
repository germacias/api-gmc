package com.nisum.test.apigmc.utils;

import java.nio.charset.StandardCharsets;
import java.util.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class TokenUtils {

    private static String secretToken;
    private static Long expirationMilliseconds = null;


    @Autowired
    public TokenUtils(@Value("${access.token.secret}") String secretToken,
                       @Value("${access.token.validity.seconds}") long expirationMilliseconds) {
        TokenUtils.secretToken = secretToken;
        TokenUtils.expirationMilliseconds = expirationMilliseconds;
    }

    public static String createToken(String name, String email){
        long expirationTime = expirationMilliseconds * 1_000L;
        Date expirationDate = new Date(System.currentTimeMillis()+expirationTime);

        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("email",email);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .addClaims(map)
                .signWith(Keys.hmacShaKeyFor(TokenUtils.secretToken.getBytes()))
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(TokenUtils.secretToken.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();

            return new UsernamePasswordAuthenticationToken(email,null, Collections.emptyList());

        }catch(JwtException e){
            return null;
        }
    }

    public static boolean isExpired(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(TokenUtils.secretToken.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

        Date expirationDate = claims.getExpiration();

        return new Date().compareTo(expirationDate)>=0;
    }

}
