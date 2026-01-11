package com.example.glamour_connect.integration;

import com.example.glamour_connect.domain.AppUser;
import com.example.glamour_connect.domain.Makeup;
import com.example.glamour_connect.domain.Review;
import com.example.glamour_connect.repository.AppUserRepository;
import com.example.glamour_connect.repository.MakeupRepository;
import com.example.glamour_connect.repository.ReviewRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppointmentsControllerIT {

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@Autowired
	ReviewRepository reviewRepository;

	@Autowired
	MakeupRepository makeupRepository;

	@Autowired
	AppUserRepository appUserRepository;

	@Test
	void deleteReview() {

		Makeup makeup = new Makeup();
		Makeup savedMakeup = makeupRepository.save(makeup);

		AppUser user = appUserRepository.findById(1L).orElseThrow();

		Review review = new Review();
		review.setMakeup(savedMakeup);
		review.setAppUser(user);

		Review savedReview = reviewRepository.save(review);

		List<Review> reviewList = reviewRepository.findAll();

		assertFalse(reviewList.isEmpty());

		given()
				.auth().basic("stefan@example.com", "parolaLuiStefan")
				.contentType(ContentType.JSON)
				.when()
				.pathParams("id", savedReview.getId())
				.delete("/review/delete/{id}")
				.then()
				.statusCode(200);

		assertTrue(reviewRepository.findAll().isEmpty());
	}
}
