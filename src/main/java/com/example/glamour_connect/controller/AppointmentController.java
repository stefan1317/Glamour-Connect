package com.example.glamour_connect.controller;

import com.example.glamour_connect.dto.AppointmentDto;
import com.example.glamour_connect.service.AppointmentService;
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
@RequestMapping("appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/save")
    public ResponseEntity<AppointmentDto> saveAppointment(@RequestBody AppointmentDto appointment) {
        return appointmentService.saveAppointment(appointment);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get-appointments/{email}")
    public ResponseEntity<List<AppointmentDto>> getAppointmentsByEmail(@PathVariable String email) {
        return appointmentService.getAppointmentsByEmail(email);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAppointments(@RequestBody List<Long> appointmentsIds) {
        return appointmentService.deleteAppointments(appointmentsIds);
    }
}
