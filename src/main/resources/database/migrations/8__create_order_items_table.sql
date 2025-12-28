CREATE TABLE `order_items` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `product_id` bigint DEFAULT NULL,
    `order_id` bigint DEFAULT NULL,
    `quantity` int COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `unit_price` decimal(10,2) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `total_price` decimal(10,2) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `order_items_product_id_foreign` (`product_id`),
    KEY `order_items_order_id_foreign` (`order_id`),
    CONSTRAINT `order_items_order_id_foreign` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
    CONSTRAINT `order_items_product_id_foreign` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;