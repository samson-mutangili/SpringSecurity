package com.samson.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtService jwtService;
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {

		//get the auth token
		final String authHeader = request.getHeader("Authorization");
		
		final String jwt;
		final String userEmail;
		
		//check if JWT token exists. If it does not exist, do not process the next steps.
		if(authHeader == null || !authHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return; //do not continue execution since no token has been provided.
		}
		
		//extract token from authorization header
		jwt = authHeader.substring(7);
		//extract user email
		userEmail = jwtService.extractUsername(jwt);
		
	}

}
