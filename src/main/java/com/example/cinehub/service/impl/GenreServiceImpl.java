package com.example.cinehub.service.impl;

import com.example.cinehub.dto.GenreDTO;
import com.example.cinehub.entity.Genre;
import com.example.cinehub.repository.GenreRepository;
import com.example.cinehub.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<GenreDTO> getAllGenres(){
        List<Genre> genres = genreRepository.findAll();
        return genres.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public GenreDTO createGenre(Genre genre) {
        Genre saveGenre = genreRepository.save(genre);
        return convertToDTO(saveGenre);
    }

    // Hàm Convert ừ Genre Entity sang GenreDTO
    private GenreDTO convertToDTO(Genre genre) {
        GenreDTO dto = new GenreDTO();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        dto.setSlug(genre.getSlug());
        return dto;
    }

}
