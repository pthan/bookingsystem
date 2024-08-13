-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Aug 13, 2024 at 02:33 PM
-- Server version: 8.2.0
-- PHP Version: 8.1.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bookingsystem`
--
CREATE DATABASE IF NOT EXISTS `bookingsystem` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `bookingsystem`;

-- --------------------------------------------------------

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
CREATE TABLE IF NOT EXISTS `country` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `country_name` varchar(255) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `guid` varchar(255) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `country`
--

INSERT INTO `country` (`id`, `country_name`, `created_by`, `created_on`, `guid`, `status`, `updated_by`, `updated_on`) VALUES
(1, 'Myanmar', NULL, '2024-08-13 16:59:45.735547', '989d52fd-6f74-4df6-bd79-e0cf8d5d9c26', 'Active', NULL, NULL),
(2, 'Singapore', NULL, '2024-08-13 16:59:45.776389', 'ea8921e4-b040-4bc8-8480-f29581df7e9e', 'Active', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `package`
--

DROP TABLE IF EXISTS `package`;
CREATE TABLE IF NOT EXISTS `package` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `credit` int NOT NULL,
  `expire_in` int NOT NULL,
  `guid` varchar(255) NOT NULL,
  `package_name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `status` varchar(255) NOT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `country_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4fwf2urehd8fnl75bmi1y7bxp` (`country_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `package`
--

INSERT INTO `package` (`id`, `created_by`, `created_on`, `credit`, `expire_in`, `guid`, `package_name`, `price`, `status`, `updated_by`, `updated_on`, `country_id`) VALUES
(1, NULL, '2024-08-13 17:01:45.779435', 5, 60, '4c96407e-8cfa-429c-a138-df9d8e548016', 'Basic MM Package', 100000, 'Active', NULL, NULL, 1),
(2, NULL, '2024-08-13 17:01:45.785436', 20, 90, '1dd395b1-a075-4ee0-b420-40414cd37337', 'Advance MM Package', 200000, 'Active', NULL, NULL, 1),
(4, NULL, '2024-08-13 17:01:45.793964', 5, 60, 'b46f05ef-c9dd-4a3f-9e37-7c26803f2558', 'Basic Sg Package', 10, 'Active', NULL, NULL, 2),
(5, NULL, '2024-08-13 17:01:45.797226', 50, 90, '37b08313-7c98-4eb4-b8fe-a075e7ab91fe', 'Advance SG Package', 20, 'Active', NULL, NULL, 2),
(10, NULL, '2024-08-13 17:01:47.508316', 50, 2, 'f89c320e-0ce3-4a6c-a1fe-62f88d5c0418', 'Flash SG Package', 20, 'Active', NULL, NULL, 2);

-- --------------------------------------------------------

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
CREATE TABLE IF NOT EXISTS `permission` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `guid` varchar(255) DEFAULT NULL,
  `permission_name` varchar(255) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `permission`
--

INSERT INTO `permission` (`id`, `created_by`, `created_on`, `guid`, `permission_name`, `status`, `updated_by`, `updated_on`) VALUES
(1, NULL, '2024-08-13 17:38:02.664476', 'e9f1d30f-d4bf-4f3b-a709-b8f5ee110156', 'READ_PRIVILEGES', 'Active', NULL, NULL),
(2, NULL, '2024-08-13 17:38:02.664476', '93f8e990-2eda-439e-812b-bde16dfef30b', 'WRITE_PRIVILEGES', 'Active', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `guid` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `created_by`, `created_on`, `guid`, `name`, `status`, `updated_by`, `updated_on`) VALUES
(1, NULL, '2024-08-13 17:59:37.042893', 'b6e61271-8502-4c6e-89f3-041e983c4971', 'ROLE_USER', 'Active', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `rolepermission`
--

DROP TABLE IF EXISTS `rolepermission`;
CREATE TABLE IF NOT EXISTS `rolepermission` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `guid` varchar(255) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `permission_id` bigint DEFAULT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK14kirk7t76s89r7er6c6ircbe` (`permission_id`),
  KEY `FKfa75v4h0djvvrq0pqrh0x9n3m` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `rolepermission`
--

INSERT INTO `rolepermission` (`id`, `created_by`, `created_on`, `guid`, `status`, `updated_by`, `updated_on`, `permission_id`, `role_id`) VALUES
(1, NULL, '2024-08-13 17:59:37.435430', '49c76053-df80-4ba4-ba7e-6c32536aed07', 'Active', NULL, NULL, 1, 1),
(2, NULL, '2024-08-13 17:59:37.450091', 'f8fab04a-5d51-40a9-a0cf-e0c79aa300e8', 'Active', NULL, NULL, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `guid` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `created_by`, `created_on`, `email`, `guid`, `name`, `password`, `status`, `updated_by`, `updated_on`, `role_id`, `username`, `user_name`) VALUES
(3, NULL, '2024-08-13 18:00:25.820449', 'pth1@gmail.com', '5d6c50ab-a774-40fd-bafe-73bbf936382f', 'pth', '$2a$10$02BQHxS0zNlqCg7eEsDOQepXsylBugiM1ka6aJaBmPP/8rmgpQKCy', 'Active', NULL, NULL, 1, NULL, 'pth'),
(4, NULL, '2024-08-13 18:00:46.198465', 'pth2@gmail.com', 'a471711e-1b15-4ac7-ab67-454863077249', 'pth2', '$2a$10$eN7jHiZD2zo.3LizEWcPKOvvN7mbozTzR5Cr78dXJd9re2mtJ9622', 'Active', NULL, NULL, 1, NULL, NULL),
(5, NULL, '2024-08-13 18:00:04.280530', 'pth3@gmail.com', '70c46f78-8854-4521-b891-9a768f0988e0', 'pth3', '$2a$10$8kS6pDTxTGNoTVHdrBaT3.Yx05wPJxpYJiyoEcoyONKEwbg7SKYYm', 'Active', NULL, NULL, 1, NULL, NULL);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `package`
--
ALTER TABLE `package`
  ADD CONSTRAINT `FK4fwf2urehd8fnl75bmi1y7bxp` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`);

--
-- Constraints for table `rolepermission`
--
ALTER TABLE `rolepermission`
  ADD CONSTRAINT `FK14kirk7t76s89r7er6c6ircbe` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
  ADD CONSTRAINT `FKfa75v4h0djvvrq0pqrh0x9n3m` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
