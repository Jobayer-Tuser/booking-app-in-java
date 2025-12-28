CREATE TABLE `cart_items` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `cart_id` binary(16) NOT NULL,
    `product_id` bigint NOT NULL,
    `quantity` int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `cart_items_cart_id_foreign` (`cart_id`),
    KEY `cart_items_product_id_foreign` (`product_id`),
    CONSTRAINT `cart_items_product_id_foreign` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT `cart_items_cart_id_foreign` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;