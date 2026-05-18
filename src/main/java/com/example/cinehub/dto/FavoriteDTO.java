package com.example.cinehub.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FavoriteDTO {
    private Long movieId;
    private String movieTilte;
    private String posterUrl;
    private LocalDateTime addedAt;
}
