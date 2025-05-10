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
-- Dumping data for table `booking`
--
CREATE DATABASE IF NOT EXISTS bookingsystem;
USE bookingsystem;

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (1,1,1,8,'3cf0ab28-caac-4655-9509-dad0dfbccc50','PAID','CANCEL','Active',1,'2025-05-10 06:23:11.636732',1,'2025-05-10 06:39:29.842694'),(2,1,5,8,'659eaec7-1b1c-48c8-85c5-b9e8fa53d6e9','PAID','BOOKED','Active',5,'2025-05-10 06:27:54.569054',NULL,NULL),(3,1,6,8,'c0241d34-16df-4b83-8655-ae50e9e5bfac','HOLD','BOOKED','Active',6,'2025-05-10 06:32:18.910717',1,'2025-05-10 06:39:29.874984');
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `booking_package_usage`
--

LOCK TABLES `booking_package_usage` WRITE;
/*!40000 ALTER TABLE `booking_package_usage` DISABLE KEYS */;
INSERT INTO `booking_package_usage` VALUES (1,1,8,1,1),(2,1,8,2,3),(3,1,8,3,4);
/*!40000 ALTER TABLE `booking_package_usage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `class_schedule`
--

LOCK TABLES `class_schedule` WRITE;
/*!40000 ALTER TABLE `class_schedule` DISABLE KEYS */;
INSERT INTO `class_schedule` VALUES (1,1,2,'2025-05-12 09:30:00.000000','2025-06-10 11:00:00.000000',2,0,'Progress','Active','5816ae8a-4d23-4226-a658-2f18902da7d1',2,'2025-05-10 05:55:56.944279',NULL,NULL),(2,3,2,'2025-05-20 09:30:00.000000','2025-07-10 11:00:00.000000',0,0,'Progress','Active','2f943baf-f5bc-42fd-b0e8-62c53d056b52',2,'2025-05-10 05:57:21.862111',NULL,NULL),(3,4,2,'2025-05-20 09:30:00.000000','2025-07-10 11:00:00.000000',0,0,'Progress','Active','709906c0-1800-4cdc-88ae-f5ab9cf6562e',2,'2025-05-10 05:57:48.090675',NULL,NULL),(4,3,2,'2025-05-12 09:30:00.000000','2025-06-10 11:00:00.000000',0,0,'Progress','Active','c36a30eb-bcd0-4a11-8807-e4be94594fa0',2,'2025-05-10 05:58:10.519677',NULL,NULL);
/*!40000 ALTER TABLE `class_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `class_schedule_detail`
--

LOCK TABLES `class_schedule_detail` WRITE;
/*!40000 ALTER TABLE `class_schedule_detail` DISABLE KEYS */;
INSERT INTO `class_schedule_detail` VALUES (1,'Mon','11:00','9:30','b0872d8c-4d25-4cdd-94b9-4df1233cda39','Active',2,'2025-05-10 05:55:56.945348',NULL,NULL,1),(2,'Tue','11:00','9:30','19a38042-8635-4137-9b31-ecbdbb851262','Active',2,'2025-05-10 05:55:56.945348',NULL,NULL,1),(3,'Tue','11:00','9:30','b07b7ab6-bce5-41f5-9b6b-3c0485162651','Active',2,'2025-05-10 05:57:21.862111',NULL,NULL,2),(4,'Wed','11:00','9:30','1f745af4-0794-4e11-82da-6a96177a4b87','Active',2,'2025-05-10 05:57:21.862111',NULL,NULL,2),(5,'Tue','11:00','9:30','1f913100-962f-4ed1-b324-9c8008630634','Active',2,'2025-05-10 05:57:48.090675',NULL,NULL,3),(6,'Wed','11:00','9:30','2732b3fd-d70c-4897-b40c-f392b412ebfe','Active',2,'2025-05-10 05:57:48.090675',NULL,NULL,3),(7,'Mon','11:00','9:30','3b0e0242-7012-4964-8b36-f3cf02ad9def','Active',2,'2025-05-10 05:58:10.519677',NULL,NULL,4),(8,'Tue','11:00','9:30','78cbe82b-cd8f-4cb5-9b1b-169d48cec25a','Active',2,'2025-05-10 05:58:10.519677',NULL,NULL,4);
/*!40000 ALTER TABLE `class_schedule_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `classinfo`
--

LOCK TABLES `classinfo` WRITE;
/*!40000 ALTER TABLE `classinfo` DISABLE KEYS */;
INSERT INTO `classinfo` VALUES (1,1,'Myanmar Yoga Class','During 20 hr durations you will get require training',20,'040ba39c-383d-405f-8bb7-9026f1f63e1e',8,'Active',2,'2025-05-10 05:51:07.253080',NULL,NULL),(2,2,'SGD Yoga Class','During 20 hr durations you will get require training',20,'7f40cc93-4fa9-4e30-a82d-3511c17efff0',8,'Active',2,'2025-05-10 05:51:32.449618',NULL,NULL),(3,1,'Gym Body Transformation Class','During 40 hr durations you will get require training',40,'20aef2de-ed1c-41dd-9203-e43e27d61827',15,'Active',2,'2025-05-10 05:52:26.036719',NULL,NULL),(4,2,'Special Gym Body Transformation Class','During 40 hr durations you will get require training',45,'dd9948c2-dedd-4978-b8ab-db6f0c3443e5',15,'Active',2,'2025-05-10 05:52:55.809582',NULL,NULL);
/*!40000 ALTER TABLE `classinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (1,'Myanmar','989d52fd-6f74-4df6-bd79-e0cf8d5d9c26','Active',NULL,'2024-05-10 16:59:45.735547',NULL,NULL),(2,'Singapore','ea8921e4-b040-4bc8-8480-f29581df7e9e','Active',NULL,'2025-05-10 16:59:45.776389',NULL,NULL),(3,'Thailand','f025fb55-be3e-4a10-b984-1e645cf12add','Active',NULL,'2025-05-10 16:59:45.776389',NULL,NULL);
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `credit_package`
--

LOCK TABLES `credit_package` WRITE;
/*!40000 ALTER TABLE `credit_package` DISABLE KEYS */;
INSERT INTO `credit_package` VALUES (1,1,'Myanmar Basic Package 10 Credit(1 month)',100000,10,30,'99e88669-bc40-469b-8ec1-48b1bb05182a','Active',2,'2025-05-10 05:39:31.308661',NULL,NULL),(2,1,'Myanmar Advance Package 30 Credit(3 month)',200000,30,90,'25a9fe4a-d6d6-4b02-893e-839d0b765354','Active',2,'2025-05-10 05:40:28.908641',NULL,NULL),(3,2,'SGD Basic Package 10 Credit(1 month)',500000,10,30,'661d62e6-9653-4d42-992a-ece7ad351aa8','Active',2,'2025-05-10 05:40:58.257700',NULL,NULL),(4,2,'SGD Advance Package 30 Credit(3 month)',1000000,30,90,'f6ccc8ba-8745-44f4-9d6a-1a82d2ca967d','Active',2,'2025-05-10 05:41:50.163038',NULL,NULL),(5,3,'THAI Advance Package 30 Credit(3 month)',800000,30,90,'d577c178-25cb-4526-8a04-e8ae18a369b3','Active',2,'2025-05-10 05:43:58.001939',NULL,NULL);
/*!40000 ALTER TABLE `credit_package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'USER_READ','Active','e9f1d30f-d4bf-4f3b-a709-b8f5ee110156',NULL,'2025-05-07 17:38:02.664476',NULL,NULL),(2,'USER_WRITE','Active','93f8e990-2eda-439e-812b-bde16dfef30b',NULL,'2025-05-07 17:38:02.664476',NULL,NULL),(3,'ADMIN_READ','Active','34fb1f74-0c7d-4442-84b3-2569d1afb765',NULL,'2025-05-07 17:38:02.664476',NULL,NULL),(4,'ADMIN_WRITE','Active','fad1d2f4-b5b0-4ac9-9130-52688da503d3',NULL,'2025-05-07 17:38:02.664476',NULL,NULL);
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `purchase_credit_package`
--

LOCK TABLES `purchase_credit_package` WRITE;
/*!40000 ALTER TABLE `purchase_credit_package` DISABLE KEYS */;
INSERT INTO `purchase_credit_package` VALUES (1,1,1,100000,10,'2025-06-09 06:20:16.338867',30,'f7cd3c43-2cde-46fa-b25c-e1df7b6a8ef3','PAID',10,'Active',1,'2025-05-10 06:20:16.338867',NULL,NULL),(2,3,1,500000,10,'2025-06-09 06:20:49.330574',30,'53e04bf8-a274-4c71-862e-217aa9cb4bfa','PAID',10,'Active',1,'2025-05-10 06:20:49.330574',NULL,NULL),(3,1,5,100000,10,'2025-06-09 06:25:29.890699',30,'4373ca62-a3b3-4bd6-910f-bbbaa8758724','PAID',2,'Active',5,'2025-05-10 06:25:29.890699',NULL,NULL),(4,1,6,100000,10,'2025-06-09 06:29:59.061840',30,'93cdaff4-eb16-4865-9c8d-b01f3c2c5be1','PAID',2,'Active',6,'2025-05-10 06:29:59.061840',NULL,NULL);
/*!40000 ALTER TABLE `purchase_credit_package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_USER','Active','b6e61271-8502-4c6e-89f3-041e983c4971',NULL,'2025-05-07 17:59:37.042893',NULL,NULL),(2,'ROLE_ADMIN','Active','40c45e84-ae25-41c8-8d47-b09f834a8b0a',NULL,'2025-05-07 17:59:37.042893',NULL,NULL);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `rolepermission`
--

LOCK TABLES `rolepermission` WRITE;
/*!40000 ALTER TABLE `rolepermission` DISABLE KEYS */;
INSERT INTO `rolepermission` VALUES (1,1,1,'49c76053-df80-4ba4-ba7e-6c32536aed07','Active',NULL,'2025-05-07 17:59:37.450091',NULL,NULL),(2,2,1,'f8fab04a-5d51-40a9-a0cf-e0c79aa300e8','Active',NULL,'2025-05-07 17:59:37.450091',NULL,NULL),(3,3,2,'9d74755a-b185-438b-8811-a26ace5dd555','Active',NULL,'2025-05-07 17:59:37.450091',NULL,NULL),(4,4,2,'1e2aca70-7cdc-4dbe-8854-6af3d4bdecc4','Active',NULL,'2025-05-07 17:59:37.450091',NULL,NULL);
/*!40000 ALTER TABLE `rolepermission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,NULL,'2025-05-10 05:09:42.287309','user1@gmail.com','c9ca2b44-7fdb-4f6f-944e-bba713f60e97','User 1','$2a$10$hIaz0j6f5eRF.s/28b5m9eGTn3zk9FvMiBTA87FdzuWUsswD66/Jy','Active',NULL,NULL,1),(2,NULL,'2025-05-10 05:10:58.608671','admin1@gmail.com','21cd846d-b5c9-419a-abdf-0b05986f3c4f','Admin','$2a$10$7tQHz7rdMDaG1Epc9S9/mej5eV26.SXoiNuutzcfDh26/GGe1m8fi','Active',NULL,NULL,2),(3,NULL,'2025-05-10 05:14:27.659960','admin2@gmail.com','96b9611a-53cb-4f28-8c5b-46dffef2a1f9','Admin','$2a$10$rCCHNr./oSBhM76QRHWG6evfLS41pVZWxNZUKIdtR8/5WShRhRzY6','Active',NULL,NULL,2),(4,NULL,'2025-05-10 05:37:26.116427','admin3@gmail.com','3d6b83d8-b1bf-4f68-97d3-3ecd6ca4e21d','Admin 2','$2a$10$HvnUp5K6AZNJT5Q87I0YXuXGQphTUEE3raTENtVrBuXW78k.GpMXG','Active',NULL,NULL,2),(5,NULL,'2025-05-10 06:01:25.529751','user3@gmail.com','fcee044d-bddc-4fa1-9295-948c83c1d737','User 3','$2a$10$dVG/9GyBwkNKIcDq83FFjeSKsK90Cn10uTOufS8Aq3TgJ4hTYC4Oi','Active',NULL,NULL,1),(6,NULL,'2025-05-10 06:28:56.485714','user2@gmail.com','7d45723b-8bff-43c8-bff1-5eb8464bb2fb','user2','$2a$10$ObZvtWIvG34jH/rL3a3iV.wl541.hqDTuX0CzupTkGYftGkaBEzBO','Active',NULL,NULL,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-10 13:11:37
