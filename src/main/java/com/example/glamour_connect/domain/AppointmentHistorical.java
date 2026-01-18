package com.example.glamour_connect.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class AppointmentHistorical extends Appointment{

    @Column
    private LocalDateTime historicalDate;
}
