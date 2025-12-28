CREATE TABLE `carts` (
     `id` binary(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
     `created_at` TIMESTAMP NOT NULL DEFAULT (curdate()),
     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;