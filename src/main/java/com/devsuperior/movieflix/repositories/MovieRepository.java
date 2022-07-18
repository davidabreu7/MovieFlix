package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {


    @Query("SELECT m FROM Movie m WHERE " +
    "(:genre IS NULL OR m.genre.id = :genre) " +
    "ORDER BY m.title ASC")
    Page<Movie> findAll(Pageable pageable, Long genre);
}