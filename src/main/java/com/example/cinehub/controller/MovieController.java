package com.example.cinehub.controller;

import com.example.cinehub.constant.Movie.MovieType;
import com.example.cinehub.dto.MovieDTO;
import com.example.cinehub.dto.PageResponse;
import com.example.cinehub.entity.Movie;
import com.example.cinehub.repository.MovieRepository;
import com.example.cinehub.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:5173") // Cấp quyền cho Vite gọi API
public class MovieController {

    @Autowired
    private MovieService movieService; // Chỉ tiêm Service, tuyệt đối không tiêm Repository ở đây nữa!

    @GetMapping
    public ResponseEntity<PageResponse<MovieDTO>> getMovies(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(movieService.getAllMoviesPaged(page, size));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MovieDTO>> getAllMooviesForAdmin() {
        return ResponseEntity.ok(movieService.getAllMoviesForAdmin());
    }

    @GetMapping("/trending")
    public List<MovieDTO> getTrendingMovies() {
        return movieService.getTrendingMovies();
    }

    @GetMapping("/type")
    public ResponseEntity<List<MovieDTO>> getMoviesByMovieType(@RequestParam MovieType movieType) {
        return ResponseEntity.ok(movieService.getMoviesByMovieType(movieType));
    }
    // 🌟 API Lọc phim theo thể loại (Dùng PathVariable để lấy slug từ URL)
    @GetMapping("/genre/{slug}")
    public ResponseEntity<List<MovieDTO>> getMoviesByGenre(@PathVariable String slug) {
        List<MovieDTO> movies = movieService.getMoviesByGenre(slug);
        return ResponseEntity.ok(movies);
    }

    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.createMovie(movie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.updateMovie(id,movie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Đã xóa phim thành công");
    }
}