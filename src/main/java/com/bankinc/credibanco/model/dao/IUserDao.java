package com.bankinc.credibanco.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankinc.credibanco.model.User;
import java.util.List;


public interface IUserDao extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username); 
}