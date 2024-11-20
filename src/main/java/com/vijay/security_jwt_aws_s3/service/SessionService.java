package com.vijay.security_jwt_aws_s3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.vijay.security_jwt_aws_s3.entity.Role;
import com.vijay.security_jwt_aws_s3.entity.TokenResponse;
import com.vijay.security_jwt_aws_s3.entity.User;
import com.vijay.security_jwt_aws_s3.repo.UserRepo;

@Service
public class SessionService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepo userRepo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @PostMapping
    public TokenResponse signIn(User user) {
        return jwtService.generateToken(user);
    }

    public User signUp(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = user.getRole() != null ? user.getRole() : Role.USER;
        user.setRole(role);
        return userRepo.save(user);
    }

}
