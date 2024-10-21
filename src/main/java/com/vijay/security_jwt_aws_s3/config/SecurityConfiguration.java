package com.vijay.security_jwt_aws_s3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.vijay.security_jwt_aws_s3.service.UserService;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfiguration {

     private UserService userService;

     @Bean
     SecurityFilterChain securityFilterChain(HttpSecurity https) throws Exception {
          return https.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                    .httpBasic(Customizer.withDefaults())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .build();

     }

     @Bean
     AuthenticationProvider authenticationProvider() {
          DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
          daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
          daoAuthenticationProvider.setUserDetailsService(userService);
          return daoAuthenticationProvider;
     }

}
