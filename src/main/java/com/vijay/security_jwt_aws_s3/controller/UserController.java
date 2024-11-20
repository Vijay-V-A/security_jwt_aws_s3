package com.vijay.security_jwt_aws_s3.controller;

import org.springframework.web.bind.annotation.RestController;

import com.vijay.security_jwt_aws_s3.entity.User;
import com.vijay.security_jwt_aws_s3.service.UserService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class UserController {

     private UserService userService;

     @GetMapping("user")
     public ResponseEntity<List<User>> getAllUser() {
          System.out.println(userService);
          return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
     }

     @GetMapping("user/{id}")
     public ResponseEntity<User> getOneUser(@RequestParam String id) {
          return new ResponseEntity<>(userService.getOneUser(id), HttpStatus.OK);
     }

     @PostMapping("user")
     public ResponseEntity<User> postUser(@RequestBody User user) {
          return new ResponseEntity<>(userService.postUser(user), HttpStatus.CREATED);
     }

     @PutMapping("user/{id}")
     public ResponseEntity<User> updateUSer(@PathVariable String id, @RequestBody User user) {
          return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
     }

     @DeleteMapping("user/{id}")
     public ResponseEntity<String> deleteUser(@PathVariable String id) {
          return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
     }

}
