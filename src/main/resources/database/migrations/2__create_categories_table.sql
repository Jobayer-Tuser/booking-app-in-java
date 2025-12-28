CREATE TABLE `categories` (
    `id` tinyint NOT NULL AUTO_INCREMENT,
    `parent_category_id` tinyint DEFAULT NULL,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `categories_parent_category_id_foreign` (`parent_category_id`),
    CONSTRAINT `categories_parent_category_id_foreign` FOREIGN KEY (`parent_category_id`) REFERENCES `categories` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;