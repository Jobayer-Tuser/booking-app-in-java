CREATE TABLE `properties` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT,
    `owner_id` bigint unsigned NOT NULL,
    `city_id` bigint unsigned NOT NULL,
    `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `address_street` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `address_postcode` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `latitude` decimal(10,7) DEFAULT NULL,
    `longitude` decimal(10,7) DEFAULT NULL,
    `created_at` timestamp NULL DEFAULT NULL,
    `updated_at` timestamp NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `properties_owner_id_foreign` (`owner_id`),
    KEY `properties_city_id_foreign` (`city_id`),
    CONSTRAINT `properties_city_id_foreign` FOREIGN KEY (`city_id`) REFERENCES `cities` (`id`),
    CONSTRAINT `properties_owner_id_foreign` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;