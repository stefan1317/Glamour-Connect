package com.example.glamour_connect.service;

import com.example.glamour_connect.domain.AppUser;
import com.example.glamour_connect.domain.Makeup;
import com.example.glamour_connect.domain.Review;
import com.example.glamour_connect.dto.ReviewDto;
import com.example.glamour_connect.exceptions.RecordCannotBeNullException;
import com.example.glamour_connect.repository.AppUserRepository;
import com.example.glamour_connect.repository.MakeupRepository;
import com.example.glamour_connect.repository.ReviewRepository;
import com.example.glamour_connect.utils.ReviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final AppUserRepository appUserRepository;

    private final MakeupRepository makeupRepository;

    private final ReviewMapper reviewMapper;

    public ResponseEntity<ReviewDto> saveReview(ReviewDto reviewDto) {

        if (reviewDto == null) {
            throw new RecordCannotBeNullException("Review cannot be null.");
        }

        AppUser user = appUserRepository
                .findById(reviewDto.userId())
                .orElseThrow(() ->
                        new RecordCannotBeNullException("User with id " + reviewDto.userId() + " cannot be found."));

        Makeup makeup = makeupRepository
                .findById(reviewDto.makeupId())
                .orElseThrow(() ->
                        new RecordCannotBeNullException("Makeup with id " + reviewDto.makeupId() + " cannot be found."));

        Review review = reviewMapper.toEntity(reviewDto);

        review.setAppUser(user);
        review.setMakeup(makeup);

        Review savedReview = reviewRepository.save(review);

        log.info("Review: {} saved.", savedReview);
        return new ResponseEntity<>(reviewMapper.toDto(savedReview), HttpStatus.OK);
    }

    public ResponseEntity<List<ReviewDto>> getReviewsByMakeupId(long makeupId) {

        List<Review> reviews = reviewRepository.findByMakeupId(makeupId);

        return new ResponseEntity<>(reviewMapper.toDtoList(reviews), HttpStatus.OK);
    }

    public ResponseEntity<String> deleteReview(long reviewId) {
        reviewRepository.deleteById(reviewId);

        return ResponseEntity.ok("Review with id " + reviewId + " was deleted successfully.");
    }
}
