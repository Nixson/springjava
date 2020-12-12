package com.example.sweater.repos;

import com.example.sweater.domain.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface shipRepo extends JpaRepository<Ship, Long> {

     List<Ship> findAllByUser(Long user);
}