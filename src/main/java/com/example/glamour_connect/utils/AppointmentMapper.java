package com.example.glamour_connect.utils;

import com.example.glamour_connect.domain.Appointment;
import com.example.glamour_connect.dto.AppointmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(target = "userId", source = "appUser.id")
    @Mapping(target = "makeupId", source = "makeup.id")
    AppointmentDto toDto(Appointment appointment);

    Appointment toEntity(AppointmentDto appointmentDto);

    List<AppointmentDto> toDtoList(List<Appointment> appointments);

    List<Appointment> toEntityList(List<AppointmentDto> dto);
}
