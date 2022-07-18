package com.devsuperior.movieflix.controller;

import com.devsuperior.movieflix.dto.MovieDto;
import com.devsuperior.movieflix.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

//    @PageableDefault(sort = "title", direction = Sort.Direction.ASC)
    @GetMapping
    public ResponseEntity<Page<MovieDto>> findAll(Pageable pageable, @RequestParam(required = false) Long genreId) {
        return ResponseEntity.ok(movieService.findAll(pageable, genreId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findById(id));
    }

}
