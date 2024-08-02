package com.example.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.travel.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
