package com.example.cinehub.service;

import com.example.cinehub.constant.Movie.MovieStatus;
import com.example.cinehub.constant.Movie.MovieType;
import com.example.cinehub.dto.MovieDTO;
import com.example.cinehub.dto.PageResponse;
import com.example.cinehub.entity.Movie;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {
    List<MovieDTO> getAllMoviesForAdmin();
    List<MovieDTO> getTrendingMovies();
    List<MovieDTO> getMoviesByMovieType(MovieType movieType);
    List<MovieDTO> getMoviesByGenre(String genreSlug);
    PageResponse<MovieDTO> getAllMoviesPaged(Pageable pageable);
    PageResponse<MovieDTO> searchAndFilterMovies(String title, MovieType movieType, MovieStatus movieStatus, Pageable pageable);
    MovieDTO createMovie(Movie movie);
    MovieDTO updateMovie(Long id, Movie movie);
    void deleteMovie(Long id);
}
