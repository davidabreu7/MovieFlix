package com.devsuperior.movieflix.service;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.exceptions.ResourceNotFoundException;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final AuthService authService;

    private final MovieRepository movieRepository;

    public ReviewService(ReviewRepository reviewRepository, MovieRepository movieRepository, AuthService authService) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
        this.authService = authService;
    }

    public ReviewDTO save(ReviewDTO reviewDto) {
        User user = authService.authenticated();
        Movie movie = movieRepository.findById(reviewDto.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        Review review = new Review(reviewDto, user, movie);
        return new ReviewDTO(reviewRepository.save(review));
    }
}
