CREATE TABLE `cities` (
      `id` bigint unsigned NOT NULL AUTO_INCREMENT,
      `country_id` bigint unsigned NOT NULL,
      `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
      `latitude` decimal(10,7) DEFAULT NULL,
      `longitude` decimal(10,7) DEFAULT NULL,
      `created_at` timestamp NULL DEFAULT NULL,
      `updated_at` timestamp NULL DEFAULT NULL,
      PRIMARY KEY (`id`),
      KEY `cities_country_id_foreign` (`country_id`),
      CONSTRAINT `cities_country_id_foreign` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;