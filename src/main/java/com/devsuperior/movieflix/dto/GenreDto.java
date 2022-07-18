package com.devsuperior.movieflix.dto;

import com.devsuperior.movieflix.entities.Genre;

import java.io.Serializable;
import java.util.Objects;

public class GenreDto implements Serializable {
    private final Long id;
    private final String name;

    public GenreDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenreDto(Genre genre) {
        this.id = genre.getId();
        this.name = genre.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreDto entity = (GenreDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ")";
    }
}
