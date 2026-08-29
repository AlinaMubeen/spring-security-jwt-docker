package com.example.springSecurity.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import com.example.springSecurity.Model.MyUser;

@Repository
public interface UserRepository extends JpaRepository<MyUser,Integer> {
	Optional<MyUser> getUserByUsername(String username);
}
