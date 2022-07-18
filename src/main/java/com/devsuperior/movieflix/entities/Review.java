package com.devsuperior.movieflix.entities;

import com.devsuperior.movieflix.dto.ReviewDTO;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tb_review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public Review() {
    }

    public Review(Long id, String text, User user, Movie movie) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.movie = movie;
    }

    public Review(ReviewDTO reviewDTO, User user, Movie movie) {
        this.id = reviewDTO.getId();
        this.text = reviewDTO.getText();
        this.user = user;
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", user=" + user +
                ", movie=" + movie +
                '}';
    }
}

