CREATE TABLE `countries` (
     `id` bigint unsigned NOT NULL AUTO_INCREMENT,
     `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
     `latitude` decimal(10,7) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
     `longitude` decimal(10,7) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
     `created_at` timestamp NULL DEFAULT NULL,
     `updated_at` timestamp NULL DEFAULT NULL,
     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;