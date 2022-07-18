package com.devsuperior.movieflix.entities;

import javax.persistence.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String subTitle;
    @Column(name = "release_date")
    private Integer year;
    private String imgUrl;
    @Column(columnDefinition = "TEXT")
    private String synopsis;

    @OneToMany(mappedBy = "movie")
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public Movie() {
    }

    public Movie(Long id, String title, String subTitle, Integer year, String imgUrl, String synopsis, List<Review> reviews, Genre genre) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.year = year;
        this.imgUrl = imgUrl;
        this.synopsis = synopsis;
        this.reviews = reviews;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
    
    private Year intToYear(Integer year) {
        return Year.of(year);
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUri) {
        this.imgUrl = imgUri;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", year=" + year +
                ", imgUri='" + imgUrl + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", reviews=" + reviews +
                ", genre=" + genre +
                '}';
    }
}
