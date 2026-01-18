package com.example.glamour_connect.unit;

import com.example.glamour_connect.domain.Makeup;
import com.example.glamour_connect.dto.ReviewDto;
import com.example.glamour_connect.exceptions.RecordCannotBeNullException;
import com.example.glamour_connect.repository.MakeupRepository;
import com.example.glamour_connect.repository.ReviewRepository;
import com.example.glamour_connect.service.ReviewService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    MakeupRepository makeupRepository;

    @Autowired
    ReviewRepository reviewRepository;

    ReviewDto reviewDto = ReviewDto.builder()
            .userId(1)
            .makeupId(1)
            .rating(1)
            .comment("Comment")
            .build();

    @AfterEach
    void cleanup() {
        reviewRepository.deleteAll();
    }

    @Test
    void saveNullReview() {

        Exception exception = assertThrows(RecordCannotBeNullException.class,
                () -> reviewService.saveReview(null));

        String expectedMessage = "Review cannot be null.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void saveReviewUserNotFound() {

        ReviewDto review = ReviewDto.builder()
                .userId(7)
                .build();

        Exception exception = assertThrows(RecordCannotBeNullException.class, () -> {

            try {
                reviewService.saveReview(review).get();
            } catch (Exception e) {
                throw e.getCause();
            }
        });

        String expectedMessage = "User with id 7 cannot be found.";
        String actualMessage = exception.getMessage();

        System.out.println(actualMessage);

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void saveReviewMakeupNotFound() {

        ReviewDto review = ReviewDto.builder()
                .userId(1)
                .makeupId(7)
                .build();

        Exception exception = assertThrows(RecordCannotBeNullException.class, () -> {

            try {
                reviewService.saveReview(review).get();
            } catch (Exception e) {
                throw e.getCause();
            }
        });

        String expectedMessage = "Makeup with id 7 cannot be found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @SneakyThrows
    @Test
    void saveReview() {

        makeupRepository.save(new Makeup());

        ReviewDto result = reviewService.saveReview(reviewDto).get().getBody();

        ReviewDto.ReviewDtoBuilder review = ReviewDto.builder()
                .id(1)
                .userId(1)
                .makeupId(1)
                .rating(1)
                .comment("Comment");

        assertNotNull(result);

        assertEquals(review.id(result.id()).build(), result);

        assertNotNull(result);
    }

    @SneakyThrows
    @Test
    void getReviewsByMakeupId() {

        makeupRepository.save(new Makeup());
        ReviewDto.ReviewDtoBuilder review = ReviewDto.builder()
                .userId(1)
                .makeupId(1)
                .rating(1)
                .comment("Comment");

        ReviewDto response = reviewService.saveReview(review.build()).get().getBody();

        assertNotNull(response);

        List<ReviewDto> result  = reviewService.getReviewsByMakeupId(1L).getBody();

        assertNotNull(result);
        assertEquals(review.id(result.getFirst().id()).build(), result.getFirst());
    }

    @SneakyThrows
    @Test
    void deleteReview() {

        makeupRepository.save(new Makeup());
        ReviewDto review = ReviewDto.builder()
                .userId(1)
                .makeupId(1)
                .rating(1)
                .comment("Comment")
                .build();

        ReviewDto result = reviewService.saveReview(review).get().getBody();

        assertNotNull(result);

        String response = reviewService.deleteReview(result.id()).getBody();

        assertFalse(reviewRepository.findById(result.id()).isPresent());
        assertEquals("Review with id 1 was deleted successfully.", response);
    }
}
