package org.heymouad.bookingmanagementsystem.mappers;

import org.heymouad.bookingmanagementsystem.dtos.BookingRequestDto;
import org.heymouad.bookingmanagementsystem.dtos.BookingResponseDto;
import org.heymouad.bookingmanagementsystem.entities.Booking;
import org.heymouad.bookingmanagementsystem.entities.ClassSchedules;
import org.heymouad.bookingmanagementsystem.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;


@Mapper(componentModel = "spring", uses = {UserMapper.class, ClassScheduleMapper.class})
public interface BookingMapper {

    @Mapping(target = "user", source = "userId")
    @Mapping(target = "classSchedules", source = "classScheduleId")
    Booking toEntity(BookingRequestDto dto);


    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "classScheduleId", source = "classSchedules.id")
    @Mapping(target = "fitnessClassName", source = "classSchedules.fitnessClass.name")
    @Mapping(target = "instructorName", source = "classSchedules.instructor.user.name")
    @Mapping(target = "startTime", source = "classSchedules.startTime")
    @Mapping(target = "endTime", source = "classSchedules.endTime")
    BookingResponseDto toResponseDto(Booking booking);

    default User mapUser(UUID id) {
        return id == null ? null : User.builder().id(id).build();
    }

    default ClassSchedules mapClassSchedules(UUID id) {
        return id == null ? null : ClassSchedules.builder().id(id).build();
    }
}
