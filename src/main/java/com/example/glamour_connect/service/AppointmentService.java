package com.example.glamour_connect.service;

import com.example.glamour_connect.domain.AppUser;
import com.example.glamour_connect.domain.Appointment;
import com.example.glamour_connect.domain.Makeup;
import com.example.glamour_connect.dto.AppointmentDto;
import com.example.glamour_connect.exceptions.EmailNotValidException;
import com.example.glamour_connect.exceptions.RecordCannotBeNullException;
import com.example.glamour_connect.repository.AppUserRepository;
import com.example.glamour_connect.repository.AppointmentRepository;
import com.example.glamour_connect.repository.MakeupRepository;
import com.example.glamour_connect.utils.AppointmentMapper;
import com.example.glamour_connect.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final AppUserRepository appUserRepository;

    private final MakeupRepository makeupRepository;

    private final AppointmentMapper appointmentMapper;

    public ResponseEntity<AppointmentDto> saveAppointment(AppointmentDto appointmentDto) {

        if (appointmentDto == null) {
            throw new RecordCannotBeNullException("Appointment cannot be null.");
        }

        AppUser user = appUserRepository
                .findById(appointmentDto.userId())
                .orElseThrow(() ->
                        new RecordCannotBeNullException("User with id " + appointmentDto.userId() + " cannot be found."));

        Makeup makeup = makeupRepository
                .findById(appointmentDto.makeupId())
                .orElseThrow(() ->
                        new RecordCannotBeNullException("Makeup with id " + appointmentDto.makeupId() + " cannot be found."));

        Appointment appointment = appointmentMapper.toEntity(appointmentDto);

        appointment.setAppUser(user);
        appointment.setMakeup(makeup);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        log.info("Appointment: {} saved.", savedAppointment);
        return new ResponseEntity<>(appointmentMapper.toDto(savedAppointment), HttpStatus.OK);
    }

    public ResponseEntity<List<AppointmentDto>> getAppointmentsByEmail(String email) {

        if (email == null || !StringUtils.isEmailValid(email)) {
            throw new EmailNotValidException("This email is not valid.");
        }

        List<Appointment> appointments = appointmentRepository.findByAppUser_Email(email);

        return new ResponseEntity<>(appointmentMapper.toDtoList(appointments), HttpStatus.OK);
    }

    public ResponseEntity<String> deleteAppointments(List<Long> appointmentsIds) {
        appointmentRepository.deleteAllById(appointmentsIds);

        return ResponseEntity.ok("Appointments successfully deleted");
    }
}
