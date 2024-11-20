package com.vijay.security_jwt_aws_s3.entity;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@AllArgsConstructor
@Data
@Table(name = "users")
public class User implements UserDetails {

     @Id
     @GeneratedValue(strategy = GenerationType.UUID)
     private String id;

     @Column(unique = true)
     private String userName;

     @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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
