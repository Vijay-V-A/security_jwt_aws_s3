package com.vijay.security_jwt_aws_s3.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vijay.security_jwt_aws_s3.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
     User findByUserName(String userName);
}
