package org.booking.beds;

import org.booking.bedTypes.BedTypeDto;

public record BedDto(
        Long id,
        Long roomId,
        Long bedTypeId,
        String name,
        BedTypeDto bedType
) {}
