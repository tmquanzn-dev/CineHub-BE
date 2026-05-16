package com.example.cinehub.controller;

import com.example.cinehub.entity.Movie;
import com.example.cinehub.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:5173") // Cấp quyền cho Vite gọi API
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("/trending")
    public List<Movie> getTrendingMovies() {
        // Sau này bạn có thể viết Query lấy phim isTrending = true ở đây
        return movieRepository.findAll();
    }
}