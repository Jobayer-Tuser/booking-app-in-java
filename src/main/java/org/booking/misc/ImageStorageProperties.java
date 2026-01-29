package org.booking.misc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.image-storage")
public class ImageStorageProperties {

    private String basePath = "/Users/jobayer/Maven/booking/src/main/resources/files/";
    private Set<String> allowedMimeTypes = Set.of("image/jpeg", "image/png", "image/webp", "image/gif");
}