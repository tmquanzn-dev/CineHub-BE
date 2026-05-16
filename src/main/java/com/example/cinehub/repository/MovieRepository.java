package com.example.cinehub.repository;

import com.example.cinehub.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    // Tự động có các hàm: findAll, findById, save, delete...

    // Bạn có thể viết thêm hàm tìm phim theo loại (movie/anime)
    List<Movie> findByMovieType(String type);
}