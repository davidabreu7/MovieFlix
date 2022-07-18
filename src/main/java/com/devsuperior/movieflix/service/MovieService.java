package com.devsuperior.movieflix.service;

import com.devsuperior.movieflix.dto.MovieDto;
import com.devsuperior.movieflix.exceptions.ResourceNotFoundException;
import com.devsuperior.movieflix.repositories.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Page<MovieDto> findAll(Pageable pageable, Long genreId) {
        return movieRepository.findAll(pageable, genreId)
                .map(MovieDto::new);
    }

    public MovieDto findById(Long id ) {
        return new MovieDto(movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie id: %d not found".formatted(id))));
    }

}
