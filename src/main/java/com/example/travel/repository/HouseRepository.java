package com.example.travel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.travel.entity.House;

public interface HouseRepository extends JpaRepository<House, Integer> {
  public Page<House> findByNameLike(String keyword, Pageable pageable);
}
