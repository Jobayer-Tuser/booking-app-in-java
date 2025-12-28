CREATE TABLE `products` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `category_id` tinyint DEFAULT NULL,
    `name` varchar(255) NOT NULL,
    `price` decimal(10,2) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `products_category_id_foreign` (`category_id`),
    CONSTRAINT `products_category_id_foreign` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;