package com.example.cinehub.service.impl;

import com.example.cinehub.dto.FavoriteDTO;
import com.example.cinehub.entity.Favorite;
import com.example.cinehub.entity.FavoriteId;
import com.example.cinehub.entity.Movie;
import com.example.cinehub.entity.User;
import com.example.cinehub.repository.FavoriteRepository;
import com.example.cinehub.repository.MovieRepository;
import com.example.cinehub.repository.UserRepository;
import com.example.cinehub.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired private FavoriteRepository favoriteRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private MovieRepository movieRepository;

    @Override
    public List<FavoriteDTO> getFavoritesByUser(Long userId) {
        List<Favorite> favorites = favoriteRepository.findByIdUserId(userId);
        return favorites.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public FavoriteDTO addFavorite(Long userId, Long movieId) {
        //Kiểm tra xem thích chưa. nếu rồi thì báo lỗi
        if (favoriteRepository.existsByIdUserIdAndIdMovieId(userId, movieId))
            throw  new RuntimeException("Phim đã có trong danh sách yêu thích");
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("Không tìm thấy User có ID = " + userId));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy phim"));
        Favorite favorite = new Favorite();
        favorite.setId(new FavoriteId(userId, movieId));
        favorite.setUser(user);
        favorite.setMovie(movie);

        return convertToDTO(favoriteRepository.save(favorite));
    }

    @Override
    public void deleteFavorite(Long userId, Long movieId) {
        favoriteRepository.deleteByUserIdAndMovieId(userId, movieId);
    }

    @Override
    public boolean isFavorited(Long userId, Long movieId) {
        return favoriteRepository.existsByIdUserIdAndIdMovieId(userId, movieId);
    }

    private FavoriteDTO convertToDTO(Favorite favorite) {
        FavoriteDTO dto = new FavoriteDTO();
        dto.setMovieId(favorite.getMovie().getId());
        dto.setMovieTitle(favorite.getMovie().getTitle());
        dto.setPosterUrl(favorite.getMovie().getPosterUrl());
        dto.setAddedAt(favorite.getAddedAt());
        return dto;
    }
}
