package com.example.glamour_connect.unit;

import com.example.glamour_connect.domain.Makeup;
import com.example.glamour_connect.dto.AppointmentDto;
import com.example.glamour_connect.exceptions.EmailNotValidException;
import com.example.glamour_connect.exceptions.RecordCannotBeNullException;
import com.example.glamour_connect.repository.AppointmentRepository;
import com.example.glamour_connect.repository.MakeupRepository;
import com.example.glamour_connect.service.AppointmentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AppointmentServiceTest {

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    MakeupRepository makeupRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    AppointmentDto appointmentDto = AppointmentDto.builder()
            .id(1)
            .durationInHours(1)
            .makeupId(1)
            .status("PENDING")
            .userId(3)
            .dateTime(LocalDateTime.of(10,10,10,10,10,10))
            .build();

    @AfterEach
    void cleanup() {
        appointmentRepository.deleteAll();
    }

    @Test
    void saveNullAppointment() {

        Exception exception = assertThrows(RecordCannotBeNullException.class,
                () -> appointmentService.saveAppointment(null));

        String expectedMessage = "Appointment cannot be null.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void saveAppointmentUserNotFound() {

        Exception exception = assertThrows(RecordCannotBeNullException.class,
                () -> appointmentService.saveAppointment(appointmentDto));

        String expectedMessage = "User with id 3 cannot be found.";
        String actualMessage = exception.getMessage();

        System.out.println(actualMessage);

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void saveAppointmentMakeupNotFound() {

        AppointmentDto appointment = AppointmentDto.builder()
                .userId(1)
                .makeupId(7)
                .build();

        Exception exception = assertThrows(RecordCannotBeNullException.class,
                () -> appointmentService.saveAppointment(appointment));

        String expectedMessage = "Makeup with id 7 cannot be found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void saveAppointment() {

        makeupRepository.save(new Makeup());
        AppointmentDto appointment = AppointmentDto.builder()
                .status("PENDING")
                .durationInHours(1)
                .userId(1)
                .makeupId(1)
                .build();

        AppointmentDto result = appointmentService.saveAppointment(appointment).getBody();

        AppointmentDto appointmentResult = AppointmentDto.builder()
                .id(2)
                .status("PENDING")
                .durationInHours(1)
                .userId(1)
                .makeupId(1)
                .build();

        assertNotNull(result);

        assertEquals(appointmentResult, result);
    }

    @Test
    void getAppointmentsInvalidEmail() {

        makeupRepository.save(new Makeup());
        AppointmentDto.AppointmentDtoBuilder appointment = AppointmentDto.builder()
                .status("PENDING")
                .durationInHours(1)
                .userId(1)
                .makeupId(1);

        AppointmentDto response = appointmentService.saveAppointment(appointment.build()).getBody();

        assertNotNull(response);

        Exception exception = assertThrows(EmailNotValidException.class,
                () -> appointmentService.getAppointmentsByEmail("invalid_email"));

        String expectedMessage = "This email is not valid.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getAppointmentsByEmail() {

        makeupRepository.save(new Makeup());
        AppointmentDto.AppointmentDtoBuilder appointment = AppointmentDto.builder()
                .status("PENDING")
                .durationInHours(1)
                .userId(1)
                .makeupId(1);

        AppointmentDto response = appointmentService.saveAppointment(appointment.build()).getBody();

        assertNotNull(response);

        List<AppointmentDto> result  = appointmentService.getAppointmentsByEmail("stefan@example.com").getBody();

        assertNotNull(result);
        assertEquals(appointment.id(response.id()).build(), result.getFirst());
    }

    @Test
    void deleteAppointments() {

        makeupRepository.save(new Makeup());
        AppointmentDto appointment = AppointmentDto.builder()
                .status("PENDING")
                .durationInHours(1)
                .userId(1)
                .makeupId(1)
                .build();

        AppointmentDto result = appointmentService.saveAppointment(appointment).getBody();

        assertNotNull(result);

        String response = appointmentService.deleteAppointments(List.of(result.id())).getBody();

        assertFalse(appointmentRepository.findById(result.id()).isPresent());
        assertEquals("Appointments successfully deleted", response);
    }
}
