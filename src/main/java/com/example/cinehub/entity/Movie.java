package com.example.cinehub.entity;

import com.example.cinehub.constant.MovieType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "movies")
@Data // Tự động tạo getter/setter từ thư viện Lombok
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "backdrop_url")
    private String backdropUrl;

    private Double rating;

    @Column(name = "is_trending")
    private Boolean isTrending;

    @Enumerated(EnumType.STRING)
    @Column(name = "movie_type")
    private MovieType movieType; // Bạn cần tạo thêm Enum MovieType (movie, series, anime)
}
