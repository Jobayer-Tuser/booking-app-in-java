package org.booking.Bed;

import org.booking.BedType.BedTypeDto;

public record BedDto(
        Long id,
        Long roomId,
        Long bedTypeId,
        String name,
        BedTypeDto bedType
) {}
