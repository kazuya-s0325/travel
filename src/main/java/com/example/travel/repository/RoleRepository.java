package com.example.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.travel.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
  public Role findByName(String name);
}
