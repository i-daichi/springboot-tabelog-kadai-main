package com.example.nagoyameshi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
