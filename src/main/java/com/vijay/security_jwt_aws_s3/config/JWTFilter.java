package com.vijay.security_jwt_aws_s3.config;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vijay.security_jwt_aws_s3.service.JWTService;
import com.vijay.security_jwt_aws_s3.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

     private final JWTService jwtService;
     private final ApplicationContext context;

     @Override
     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
               throws ServletException, IOException {

          String authHeader = request.getHeader("Authorization");
          String token = "";
          String userName = "";

          if (authHeader == null || !authHeader.startsWith("Bearer ")) {
               token = authHeader.substring(7);
               userName = jwtService.getUserNameFromToken(token);
          }

          if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
               UserDetails userDetail = context.getBean(UserService.class).loadUserByUsername(userName);

               if (jwtService.validateToken(token, userDetail)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetail,
                              null, userDetail.getAuthorities());
                    authToken.setDetails(userDetail);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
               }
          }

          filterChain.doFilter(request, response);
     }

}
