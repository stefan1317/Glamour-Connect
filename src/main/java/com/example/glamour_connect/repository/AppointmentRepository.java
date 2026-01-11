package com.example.glamour_connect.repository;

import com.example.glamour_connect.domain.Appointment;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByAppUser_Email(String email);

    void deleteById(@NonNull Long id);
}
