package com.example.cinehub.service.impl;

import com.example.cinehub.dto.ReviewDTO;
import com.example.cinehub.entity.Movie;
import com.example.cinehub.entity.Review;
import com.example.cinehub.entity.User;
import com.example.cinehub.exception.ResourceNotFoundException;
import com.example.cinehub.repository.MovieRepository;
import com.example.cinehub.repository.ReviewRepository;
import com.example.cinehub.repository.UserRepository;
import com.example.cinehub.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<ReviewDTO> getReviewsByMovieId(Long movieId) {
        List<Review> reviews = reviewRepository.findByMovieIdOrderByCreateAtDesc(movieId);
        return reviews.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public ReviewDTO addReview(Long movieId,Long userId ,Review review ) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm thấy film có ID = " + movieId));
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("Không tìm thaấy user có ID = " + userId));

        review.setMovie(movie);
        review.setUser(user);

        return convertToDTO(reviewRepository.save(review));
    }

    @Override
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm thấy bình luận có ID = "+ reviewId));
        reviewRepository.delete(review);
    }

    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setContent(review.getContent());
        dto.setRating(review.getRating());
        dto.setCreateAt(review.getCreateAt());
        if (review.getUser() != null)
        {
            dto.setUserId(review.getUser().getId());
            dto.setUsername(review.getUser().getUsername());
        }
        return  dto;
    }
}
