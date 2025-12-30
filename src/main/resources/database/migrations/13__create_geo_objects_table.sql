CREATE TABLE `geo_objects` (
   `id` bigint unsigned NOT NULL AUTO_INCREMENT,
   `city_id` bigint unsigned DEFAULT NULL,
   `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
   `latitude` decimal(10,7) DEFAULT NULL,
   `longitude` decimal(10,7) DEFAULT NULL,
   `created_at` timestamp NULL DEFAULT NULL,
   `updated_at` timestamp NULL DEFAULT NULL,
   PRIMARY KEY (`id`),
   KEY `geo_objects_city_id_foreign` (`city_id`),
   CONSTRAINT `geo_objects_city_id_foreign` FOREIGN KEY (`city_id`) REFERENCES `cities` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;