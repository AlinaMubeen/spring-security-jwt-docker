package com.example.springSecurity.Model;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name="my_user")
@AllArgsConstructor
@NoArgsConstructor
public class MyUser {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
private Integer id;
private String username;
private String Password;
private String Role;
}
