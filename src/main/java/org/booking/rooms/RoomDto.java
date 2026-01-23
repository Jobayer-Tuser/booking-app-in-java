package org.booking.rooms;

import org.booking.beds.BedDto;

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
