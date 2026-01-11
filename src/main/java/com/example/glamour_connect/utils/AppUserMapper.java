package com.example.glamour_connect.utils;

import com.example.glamour_connect.domain.AppUser;
import com.example.glamour_connect.dto.AppUserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

    AppUserDto toDto(AppUser appUser);

    AppUser toEntity(AppUserDto appUserDto);
}
