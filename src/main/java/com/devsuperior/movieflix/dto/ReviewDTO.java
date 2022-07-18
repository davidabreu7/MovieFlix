package com.devsuperior.movieflix.dto;

import com.devsuperior.movieflix.entities.Review;
import org.springframework.lang.Nullable;


import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ReviewDTO implements Serializable {
    private Long id;
    @NotBlank
    private String text;

    private Long movieId;

    @Nullable
    private UserDto user;

    public ReviewDTO() {
    }

    public ReviewDTO(Long id, String text, Long movieId, @Nullable UserDto user) {
        this.id = id;
        this.text = text;
        this.movieId = movieId;
        this.user = user;
    }

  public ReviewDTO(Review review) {
    this.id = review.getId();
    this.text = review.getText();
    this.movieId = review.getMovie().getId();
    this.user = new UserDto(review.getUser());
  }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "text = " + text + ")";
    }

}
