package com.example.glamour_connect.unit;

import com.example.glamour_connect.dto.MakeupDto;
import com.example.glamour_connect.exceptions.RecordCannotBeNullException;
import com.example.glamour_connect.repository.MakeupRepository;
import com.example.glamour_connect.service.MakeupService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MakeupServiceTest {

    @Autowired
    MakeupService makeupService;

    @Autowired
    MakeupRepository makeupRepository;

    MakeupDto makeupDto = MakeupDto.builder()
            .name("Makeup")
            .description("This is a makeup")
            .imageUrl("https://www.google.com")
            .durationInHours(10)
            .price(12.0)
            .build();

    @AfterEach
    void cleanup() {
        makeupRepository.deleteAll();
    }

    @Test
    void saveNullMakeup() {

        Exception exception = assertThrows(RecordCannotBeNullException.class,
                () -> makeupService.saveMakeup(null));

        String expectedMessage = "Makeup cannot be null.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void saveMakeup() {

        MakeupDto result = makeupService.saveMakeup(makeupDto).getBody();

        assertNotNull(result);

        MakeupDto makeup = MakeupDto.builder()
                .id(result.id())
                .name("Makeup")
                .description("This is a makeup")
                .imageUrl("https://www.google.com")
                .durationInHours(10)
                .price(12.0)
                .build();

        assertEquals(makeup, result);
    }
}
