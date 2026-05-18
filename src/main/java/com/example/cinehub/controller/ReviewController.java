package com.example.cinehub.controller;

import com.example.cinehub.dto.ReviewDTO;
import com.example.cinehub.entity.Review;
import com.example.cinehub.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/movies/{movieId}/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviewsByMovieId(@PathVariable Long movieId) {
        return ResponseEntity.ok(reviewService.getReviewsByMovieId(movieId));
    }

    @PostMapping("/movies/{movieId}/reviews")
    public ResponseEntity<ReviewDTO> addReview(@PathVariable Long movieId,@RequestParam Long userId ,@RequestBody Review review) {
        return ResponseEntity.ok(reviewService.addReview(movieId, userId, review));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("Đã xóa bình luận thành công");
    }
}
