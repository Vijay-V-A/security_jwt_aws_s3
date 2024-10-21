package com.vijay.security_jwt_aws_s3.entity;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@AllArgsConstructor
@Data
public class User implements UserDetails {

     @Id
     @GeneratedValue(strategy = GenerationType.UUID)
     private String id;
     private String userName;
     private String password;
     private Role role;

     @Override
     public Collection<? extends GrantedAuthority> getAuthorities() {
          return Collections.singleton(new SimpleGrantedAuthority(this.role.toString()));
     }

     @Override
     public String getUsername() {
          return this.userName;
     }

     @Override
     public String getPassword() {
          return this.password;
     }

     @Override
     public boolean isAccountNonExpired() {
          return true;
     }

     @Override
     public boolean isAccountNonLocked() {
          return true;
     }

     @Override
     public boolean isCredentialsNonExpired() {
          return true;
     }

     @Override
     public boolean isEnabled() {
          return true;
     }
}
