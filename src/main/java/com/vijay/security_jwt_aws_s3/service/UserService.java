package com.vijay.security_jwt_aws_s3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vijay.security_jwt_aws_s3.entity.User;
import com.vijay.security_jwt_aws_s3.exception.ResourceNotFound;
import com.vijay.security_jwt_aws_s3.repo.UserRepo;

@Service
public class UserService implements UserDetailsService {

     @Autowired
     private UserRepo userRepo;

     private BCryptPasswordEncoder passwordEncoder;

     public List<User> getAllUser() {
          return userRepo.findAll();
     }

     public User getOneUser(String userId) {
          return userRepo.findById(userId).orElseThrow(() -> new ResourceNotFound("User not found : " + userId));
     }

     public User postUser(User user) {
          user.setPassword(passwordEncoder.encode(user.getPassword()));
          return userRepo.save(user);
     }

     public User updateUser(String userId, User user) {
          User existingUser = getOneUser(userId);

          existingUser.setUserName(user.getUsername());
          existingUser.setPassword(user.getPassword());
          existingUser.setRole(user.getRole());

          return userRepo.save(existingUser);
     }

     public String deleteUser(String userId) {
          User existingUser = getOneUser(userId);
          userRepo.deleteById(existingUser.getId());
          return "User deleted successfully";
     }

     @Override
     public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
          User user = userRepo.findByUserName(userName);

          if (user.getId() == null)
               throw new UsernameNotFoundException("User not found");

          return user;
     }

}
