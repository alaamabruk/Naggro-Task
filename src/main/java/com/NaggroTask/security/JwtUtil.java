package com.NaggroTask.security;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	
	@Value("${app.token.secret}")
	private String jwtTokenSecret;

	@Value("${app.token.expiration}")
	private int jwtTokenExpiration;
	
	
	@Autowired
	private StoredToken storedToken;

	
	
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtTokenSecret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
	
    
	public String generateToken(UserDetails userDetails) { Map<String, Object>
	  claims = new HashMap<>(); return createToken(claims,
	  userDetails.getUsername()); }
	 

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenExpiration * 1000 ))
                .signWith(SignatureAlgorithm.HS256, jwtTokenSecret).compact();
    }
    

    public Boolean validateToken(String token, UserDetails userDetails) {
    	final String username = extractUsername(token);
    	String storedtoken = storedToken.getTokenByUserName(username);
		if (storedtoken == null || !storedtoken.equals(token)) {
			return false;
		}
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}