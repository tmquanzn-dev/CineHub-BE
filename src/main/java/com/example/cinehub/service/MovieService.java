package com.example.cinehub.service;

import com.example.cinehub.constant.Movie.MovieType;
import com.example.cinehub.dto.MovieDTO;
import com.example.cinehub.entity.Movie;

import java.util.List;

public interface MovieService {
    List<MovieDTO> getAllMovies();
    List<MovieDTO> getTrendingMovies();
    List<MovieDTO> getMoviesByMovieType(MovieType movieType);
    List<MovieDTO> getMoviesByGenre(String genreSlug);
    MovieDTO createMovie(Movie movie);
    MovieDTO updateMovie(Long id, Movie movie);
    void deleteMovie(Long id);
}
