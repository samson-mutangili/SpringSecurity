package com.samson.config;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	
	private static final String SECRET_KEY = "iGTAuw7ZqLk6tadUlIn70ublTA92QDQqyM8eoZ1FGbI=";

	public String extractUsername(String token) {
		// TODO Auto-generated method stub
		return  extractClaim(token, Claims::getSubject);
	}

	//extract a specific claim
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		
		final Claims claims = extractAllClaims(token);
		
		return claimsResolver.apply(claims);
	}
	
	//method that generates a token with only the user details
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	//This function generates a token with extra claims and user Details
	public String generateToken(
			Map<String, Object> extraClaims,
			UserDetails userDetails
			) {
		
		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	//method that  extracts all claims
	private Claims extractAllClaims(String token) {
		
		@SuppressWarnings("deprecation")
		Claims body = Jwts.parser()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getPayload();
		
		return body;
			
	}


	private java.security.Key getSignInKey() {
		//decode the secret key
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		//return the key
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
