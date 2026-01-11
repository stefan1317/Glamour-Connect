package com.example.glamour_connect.utils;

import com.example.glamour_connect.domain.Review;
import com.example.glamour_connect.dto.ReviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "userId", source = "appUser.id")
    @Mapping(target = "makeupId", source = "makeup.id")
    ReviewDto toDto(Review review);

    Review toEntity(ReviewDto reviewDto);

    List<ReviewDto> toDtoList(List<Review> reviews);

    List<Review> toEntityList(List<ReviewDto> dto);
}
