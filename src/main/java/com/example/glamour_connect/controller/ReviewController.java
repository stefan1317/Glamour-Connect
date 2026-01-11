package com.example.glamour_connect.controller;

import com.example.glamour_connect.dto.ReviewDto;
import com.example.glamour_connect.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("review")
public class ReviewController {

    private final ReviewService reviewService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/save")
    public ResponseEntity<ReviewDto> saveReview(@RequestBody ReviewDto reviewDto) {
        return reviewService.saveReview(reviewDto);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get/{makeupId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByMakeupId(@PathVariable long makeupId) {
        return reviewService.getReviewsByMakeupId(makeupId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable long reviewId) {
        return reviewService.deleteReview(reviewId);
    }
}
