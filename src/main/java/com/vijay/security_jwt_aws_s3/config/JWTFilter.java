package com.vijay.security_jwt_aws_s3.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import com.vijay.security_jwt_aws_s3.service.JWTService;
import com.vijay.security_jwt_aws_s3.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class JWTFilter extends OncePerRequestFilter {

     private final JWTService jwtService;
     private final ApplicationContext context;

     @Override
     protected void doFilterInternal(@NonNull HttpServletRequest request,
               @NonNull HttpServletResponse response,
               @NonNull FilterChain filterChain)
               throws ServletException, IOException {

          List<String> excludedEndpoints = Arrays.asList(
                    "/api/v1/session/signUp",
                    "/api/v1/session/signIn");

          if (excludedEndpoints.contains(request.getRequestURI())) {
               filterChain.doFilter(request, response);
               return;
          }

          String authHeader = request.getHeader("Authorization");
          String token = "";
          String userName = "";

          if (authHeader != null && authHeader.startsWith("Bearer ")) {
               token = authHeader.substring(7);
               userName = jwtService.getUserNameFromToken(token);
          }

          if (userName == null)
               filterChain.doFilter(request, response);

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
