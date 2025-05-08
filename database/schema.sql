-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: bookingsystem
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `booking_status` varchar(255) NOT NULL,
  `created_by` bigint NOT NULL,
  `created_on` datetime(6) NOT NULL,
  `credit` int NOT NULL,
  `guid` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `schedule_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hr15dp4km2m4w90aegj8fig40` (`guid`),
  KEY `FK8r7e2407lvvsxygylsqg0wqgg` (`schedule_id`),
  KEY `FKkgseyy7t56x7lkjgu3wah5s3t` (`user_id`),
  CONSTRAINT `FK8r7e2407lvvsxygylsqg0wqgg` FOREIGN KEY (`schedule_id`) REFERENCES `class_schedule` (`id`),
  CONSTRAINT `FKkgseyy7t56x7lkjgu3wah5s3t` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `booking_package_usage`
--

DROP TABLE IF EXISTS `booking_package_usage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking_package_usage` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `country_id` bigint DEFAULT NULL,
  `used_credit` int DEFAULT NULL,
  `booking_id` bigint DEFAULT NULL,
  `package_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgx4g7ikw7wo0ripx20wo0fenn` (`booking_id`),
  KEY `FKrteixelpa0mikeomjlx1pm86t` (`package_id`),
  CONSTRAINT `FKgx4g7ikw7wo0ripx20wo0fenn` FOREIGN KEY (`booking_id`) REFERENCES `booking` (`id`),
  CONSTRAINT `FKrteixelpa0mikeomjlx1pm86t` FOREIGN KEY (`package_id`) REFERENCES `purchase_credit_package` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `class_schedule`
--

DROP TABLE IF EXISTS `class_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_schedule` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `available_slots` int NOT NULL,
  `booking_count` int NOT NULL,
  `created_by` bigint NOT NULL,
  `created_on` datetime(6) NOT NULL,
  `end_date` datetime(6) NOT NULL,
  `guid` varchar(255) NOT NULL,
  `start_date` datetime(6) NOT NULL,
  `status` varchar(255) NOT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `waiting_count` int NOT NULL,
  `classinfo_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6965fb3iqya5vydq66j9ip74q` (`guid`),
  KEY `FKa3wvvjlp2u8ut20dxelrjpr6i` (`classinfo_id`),
  CONSTRAINT `FKa3wvvjlp2u8ut20dxelrjpr6i` FOREIGN KEY (`classinfo_id`) REFERENCES `classinfo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `class_schedule_detail`
--

DROP TABLE IF EXISTS `class_schedule_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_schedule_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint NOT NULL,
  `created_on` datetime(6) NOT NULL,
  `guid` varchar(255) NOT NULL,
  `session_day` varchar(255) NOT NULL,
  `session_end_time` varchar(255) NOT NULL,
  `session_start_time` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `class_schedule_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ir2smgix9tf9bwdhe92bcutoj` (`guid`),
  KEY `FK72dwyfpncjvfifu1i9x7pcoy4` (`class_schedule_id`),
  CONSTRAINT `FK72dwyfpncjvfifu1i9x7pcoy4` FOREIGN KEY (`class_schedule_id`) REFERENCES `class_schedule` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `classinfo`
--

DROP TABLE IF EXISTS `classinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classinfo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `class_name` varchar(255) NOT NULL,
  `created_by` bigint NOT NULL,
  `created_on` datetime(6) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `duration` int NOT NULL,
  `guid` varchar(255) NOT NULL,
  `required_credit` int NOT NULL,
  `status` varchar(255) NOT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `country_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hidrj76f1ckkyovgsqh56ynfy` (`guid`),
  KEY `FKj5hg3586c10ittew6x478atmo` (`country_id`),
  CONSTRAINT `FKj5hg3586c10ittew6x478atmo` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `country` (
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `credit_package`
--

DROP TABLE IF EXISTS `credit_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `credit_package` (
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
  KEY `FK9h5c8la0eakm3dt9n9ap6y1gh` (`country_id`),
  CONSTRAINT `FK9h5c8la0eakm3dt9n9ap6y1gh` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `package`
--

DROP TABLE IF EXISTS `package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `package` (
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
  KEY `FK4fwf2urehd8fnl75bmi1y7bxp` (`country_id`),
  CONSTRAINT `FK4fwf2urehd8fnl75bmi1y7bxp` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `guid` varchar(255) DEFAULT NULL,
  `permission_name` varchar(255) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `purchase_credit_package`
--

DROP TABLE IF EXISTS `purchase_credit_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_credit_package` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `available_credit` int NOT NULL,
  `created_by` bigint NOT NULL,
  `created_on` datetime(6) NOT NULL,
  `expire_date` datetime(6) NOT NULL,
  `expire_in` int NOT NULL,
  `guid` varchar(255) NOT NULL,
  `payment_status` varchar(255) NOT NULL,
  `remaining_credit` int NOT NULL,
  `status` varchar(255) NOT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `package_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `country_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5rrotff37p6wq5k125dprcbx3` (`guid`),
  KEY `FKsygbk2hlbv8kbnhocqfjvjs6a` (`package_id`),
  KEY `FKlp5wtcunxiyx5xr4q6qoeklll` (`user_id`),
  KEY `FKl1yv0irhxwsb3qjr1m434hcqd` (`country_id`),
  CONSTRAINT `FKl1yv0irhxwsb3qjr1m434hcqd` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FKlp5wtcunxiyx5xr4q6qoeklll` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKsygbk2hlbv8kbnhocqfjvjs6a` FOREIGN KEY (`package_id`) REFERENCES `credit_package` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `guid` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rolepermission`
--

DROP TABLE IF EXISTS `rolepermission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rolepermission` (
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
  KEY `FKfa75v4h0djvvrq0pqrh0x9n3m` (`role_id`),
  CONSTRAINT `FK14kirk7t76s89r7er6c6ircbe` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
  CONSTRAINT `FKfa75v4h0djvvrq0pqrh0x9n3m` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
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
  KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`),
  CONSTRAINT `FKn82ha3ccdebhokx3a8fgdqeyy` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-08 23:36:41
