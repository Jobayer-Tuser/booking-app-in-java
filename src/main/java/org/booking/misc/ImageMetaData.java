package org.booking.misc;

public record ImageMetaData(
   String storagePath,
   String fileName,
   String originalName,
   String fileType,
   Long fileSize,
   byte[] fileBytes
) {}
