package org.booking.Room;

import org.booking.Bed.BedDto;

import java.time.Instant;
import java.util.Set;

public record RoomDto(
        Long id,
        Long apartmentId,
        Long roomTypeId,
        String name,
        Instant createdAt,
        Instant updatedAt,
        Set<BedDto> beds
) {}
