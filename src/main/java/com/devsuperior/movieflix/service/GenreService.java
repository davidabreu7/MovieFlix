package com.devsuperior.movieflix.service;

import com.devsuperior.movieflix.dto.GenreDto;
import com.devsuperior.movieflix.repositories.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<GenreDto> findAll() {
        return genreRepository.findAll().stream()
                .map(GenreDto::new)
                .collect(Collectors.toList());
    }
}
