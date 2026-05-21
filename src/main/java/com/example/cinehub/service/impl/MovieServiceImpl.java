package com.example.cinehub.service.impl;

import com.example.cinehub.constant.Movie.MovieType;
import com.example.cinehub.dto.GenreDTO;
import com.example.cinehub.dto.MovieDTO;
import com.example.cinehub.entity.Movie;
import com.example.cinehub.repository.MovieRepository;
import com.example.cinehub.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<MovieDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> getTrendingMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .filter(movie -> Boolean.TRUE.equals(movie.getIsTrending()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> getMoviesByMovieType(MovieType movieType) {
        List<Movie> movies = movieRepository.findByMovieType(movieType);
        return movies.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> getMoviesByGenre(String genreSlug) {
        List<Movie> movies = movieRepository.findMoviesByGenreSlug(genreSlug);

        return movies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MovieDTO createMovie(Movie movie) {
        return convertToDTO(movieRepository.save(movie));
    }

    @Override
    public MovieDTO updateMovie(Long id, Movie movieDetails) {
        Movie movie = movieRepository.findById(id).orElseThrow(()-> new RuntimeException("Không tìm thấy phim"));
        movie.setTitle(movieDetails.getTitle());
        movie.setDescription(movieDetails.getDescription());
        movie.setPosterUrl(movieDetails.getPosterUrl());
        movie.setBackdropUrl(movieDetails.getBackdropUrl());
        movie.setReleaseDate(movieDetails.getReleaseDate());
        movie.setDuration(movieDetails.getDuration());
        movie.setRating(movieDetails.getRating());
        movie.setIsTrending(movieDetails.getIsTrending());
        movie.setIsTopRated(movieDetails.getIsTopRated());
        movie.setMovieType(movieDetails.getMovieType());
        movie.setStatus(movieDetails.getStatus());

        if (movieDetails.getGenres() != null) {
            movie.setGenres(movieDetails.getGenres());
        }

        return convertToDTO(movieRepository.save(movie));
    }

    @Override
    public void deleteMovie(Long id) {
        Movie m = movieRepository.findById(id).orElseThrow(()-> new RuntimeException("Không tìm thấy phim"));
        movieRepository.delete(m);
    }
    //Ham bổ trợ đóng vai trò convert thủ công(Mapping) từ Entity sang DTO sạch sẽ
    private MovieDTO convertToDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setDescription(movie.getDescription());
        dto.setPosterUrl(movie.getPosterUrl());
        dto.setBackdropUrl(movie.getBackdropUrl());
        dto.setRating(movie.getRating());
        dto.setIsTrending(movie.getIsTrending());
        dto.setMovieType(movie.getMovieType());

        // Convert tập hợp các Genres lồng bên trong sang GenreDTO công thức song song
        if (movie.getGenres() != null) {
            Set<GenreDTO> genreDTOs = movie.getGenres().stream().map(genre -> {
                GenreDTO gDto = new GenreDTO();
                gDto.setId(genre.getId());
                gDto.setName(genre.getName());
                gDto.setSlug(genre.getSlug());
                return gDto;
            }).collect(Collectors.toSet());
            dto.setGenres(genreDTOs);
        }
        return dto;
    }



}
