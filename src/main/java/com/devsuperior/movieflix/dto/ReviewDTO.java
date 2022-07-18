package com.devsuperior.movieflix.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ReviewDTO implements Serializable {
    private Long id;
    @NotBlank
    private String text;

    private Long movieId;

    public ReviewDTO() {
    }

    public ReviewDTO(Long id, String text, Long movieId) {
        this.id = id;
        this.text = text;
        this.movieId = movieId;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String setText(String text) {
        return text;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "text = " + text + ")";
    }
}
