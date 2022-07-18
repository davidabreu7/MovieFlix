package com.devsuperior.movieflix.dto;

import com.devsuperior.movieflix.dto.GenreDto;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;

import java.io.Serializable;
import java.util.List;

public class MovieDto implements Serializable {
    private final Long id;
    private final String title;
    private final String subTitle;
    private final Integer year;
    private final String imgUrl;
    private final String synopsis;

    private final GenreDto genre;

    public MovieDto(Long id, String title, String subTitle, Integer year, String imgUrl, String synopsis, GenreDto genre) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.year = year;
        this.imgUrl = imgUrl;
        this.synopsis = synopsis;
        this.genre = genre;
    }

    public MovieDto(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.subTitle = movie.getSubTitle();
        this.year = movie.getYear();
        this.imgUrl = movie.getImgUrl();
        this.synopsis = movie.getSynopsis();
        this.genre = new GenreDto(movie.getGenre());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public Integer getYear() {
        return year;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getSynopsis() {
        return synopsis;
    }


    public GenreDto getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "title = " + title + ", " +
                "subTitle = " + subTitle + ", " +
                "year = " + year + ", " +
                "imgUrl = " + imgUrl + ", " +
                "synopsis = " + synopsis + ", " +
                "genre = " + genre + ")";
    }
}
