package com.vijay.security_jwt_aws_s3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.vijay.security_jwt_aws_s3.service.UserService;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfiguration {

     private UserService userService;
     private JWTFilter jwtFilter;

     @Bean
     SecurityFilterChain securityFilterChain(HttpSecurity https) throws Exception {
          return https.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(
                              auth -> auth.requestMatchers("api/v1/session/signUp", "api/v1/session/signIn").permitAll()
                                        .anyRequest()
                                        .authenticated())
                    .httpBasic(Customizer.withDefaults())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authenticationProvider(authenticationProvider())
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();

     }

     @Bean
     AuthenticationProvider authenticationProvider() {
          DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
          daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
          daoAuthenticationProvider.setUserDetailsService(userService);
          return daoAuthenticationProvider;
     }

     @Bean
     AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
          return configuration.getAuthenticationManager();
     }

}
