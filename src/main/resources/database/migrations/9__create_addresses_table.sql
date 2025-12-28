CREATE TABLE `addresses` (
     `id` bigint NOT NULL AUTO_INCREMENT,
     `user_id` bigint COLLATE utf8mb4_unicode_ci DEFAULT NULL,
     `street` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
     `zip` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
     `city` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
     `state` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
     PRIMARY KEY (`id`),
     KEY `addresses_user_id_foreign` (`user_id`),
     CONSTRAINT `addresses_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;