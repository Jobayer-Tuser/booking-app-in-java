CREATE TABLE `orders` (
      `id` bigint NOT NULL AUTO_INCREMENT,
      `customer_id` bigint DEFAULT NULL,
      `status` enum('Canceled','Confirmed','Delivered','Paid','Pending') COLLATE utf8mb4_unicode_ci DEFAULT 'Pending',
      `total_price` decimal(10,2) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
      `created_at` timestamp DEFAULT NULL,
      PRIMARY KEY (`id`),
      KEY `orders_customer_id_foreign` (`customer_id`),
      CONSTRAINT `orders_customer_id_foreign` FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;