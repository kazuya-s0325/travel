package com.example.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.travel.entity.House;

public interface HouseRepository extends JpaRepository<House, Integer> {

}
