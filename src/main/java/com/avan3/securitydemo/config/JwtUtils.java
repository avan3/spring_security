package com.avan3.securitydemo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtUtils {

    private String jwtSigningKey = "7IItgfu8nurqKnTF7vsi1wDRn630wmt1IXHZqTG4koMCT9OeyGsPGQ44NYv6uGZlGFt20IDAdi6qNo1uCSziQZC7rcWWNrL2QpTSNt5iCsVH8h1BfzivOJGeaBkHsBs2fZPpjTyYiKLyPwZ7uMkM5NVfXCDOa9jRRQUpB7HZnSp3es7nIOHOMOPOqbU56T2pcM2UrwSe3ltgexI4yoRuTqpZ9N82zqxJ4InetG2DgrIC4YojQS8PQzCMtneHU8oN";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean hasClaim(String token, String claimName) {
        final Claims claims = extractAllClaims(token);
        return claims.get(claimName) != null;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        // https://stackoverflow.com/a/77408683
        return Jwts.parser().setSigningKey(jwtSigningKey).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails);
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> claims) {
        return createToken(claims, userDetails);
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(200)))
                .signWith(SignatureAlgorithm.HS256, jwtSigningKey).compact();
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}