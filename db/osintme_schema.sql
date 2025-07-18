-- MySQL dump 10.13  Distrib 8.0.42, for Linux (x86_64)
--
-- Host: localhost    Database: osintme
-- ------------------------------------------------------
-- Server version	8.0.42-0ubuntu0.22.04.1

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
-- Table structure for table `Action`
--

DROP TABLE IF EXISTS `Action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Action` (
  `action_id` int NOT NULL AUTO_INCREMENT,
  `log_id` int NOT NULL,
  `description` enum('Update User Info','Deactivate User','Update Password','Add User','Password Reset') NOT NULL,
  PRIMARY KEY (`action_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Action`
--

LOCK TABLES `Action` WRITE;
/*!40000 ALTER TABLE `Action` DISABLE KEYS */;
INSERT INTO `Action` VALUES (1,1,'Add User'),(2,2,'Password Reset'),(3,3,'Update Password'),(4,4,'Update User Info'),(5,5,'Deactivate User'),(6,6,'Password Reset'),(7,7,'Add User'),(8,8,'Update Password'),(9,9,'Deactivate User'),(10,10,'Update User Info');
/*!40000 ALTER TABLE `Action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Activity_Log`
--

DROP TABLE IF EXISTS `Activity_Log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Activity_Log` (
  `log_id` int NOT NULL AUTO_INCREMENT,
  `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int NOT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Activity_Log`
--

LOCK TABLES `Activity_Log` WRITE;
/*!40000 ALTER TABLE `Activity_Log` DISABLE KEYS */;
INSERT INTO `Activity_Log` VALUES (1,'2025-07-08 00:37:47',3),(2,'2025-07-08 00:37:47',7),(3,'2025-07-08 00:37:47',10),(4,'2025-07-08 00:37:47',13),(5,'2025-07-08 00:37:47',16),(6,'2025-07-08 00:37:47',19),(7,'2025-07-08 00:37:47',2),(8,'2025-07-08 00:37:47',5),(9,'2025-07-08 00:37:47',12),(10,'2025-07-08 00:37:47',14);
/*!40000 ALTER TABLE `Activity_Log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Breach_Details`
--

DROP TABLE IF EXISTS `Breach_Details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Breach_Details` (
  `breach_id` int NOT NULL AUTO_INCREMENT,
  `breached_first_name` varchar(45) DEFAULT NULL,
  `breached_middle_name` varchar(45) DEFAULT NULL,
  `breached_last_name` varchar(45) DEFAULT NULL,
  `breached_nickname` varchar(45) DEFAULT NULL,
  `breach_source` varchar(45) DEFAULT NULL,
  `breached_email` varchar(255) DEFAULT NULL,
  `breached_phone` varchar(20) DEFAULT NULL,
  `breached_address` varchar(45) DEFAULT NULL,
  `breached_city` varchar(45) DEFAULT NULL,
  `breached_state` varchar(45) DEFAULT NULL,
  `breached_zip_code` varchar(45) DEFAULT NULL,
  `breached_birthday` datetime DEFAULT NULL,
  `exposure_date` datetime DEFAULT NULL,
  PRIMARY KEY (`breach_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Breach_Details`
--

LOCK TABLES `Breach_Details` WRITE;
/*!40000 ALTER TABLE `Breach_Details` DISABLE KEYS */;
INSERT INTO `Breach_Details` VALUES (1,'David','Michael','Chen',NULL,'HaveIBeenPwned','david.chen@sjsu.edu',NULL,NULL,'San Jose','CA',NULL,NULL,'2024-12-01 00:00:00'),(2,'Emily','Rose','Lopez',NULL,'BreachAlarm','emily.lopez@sjsu.edu',NULL,NULL,NULL,NULL,NULL,'1990-07-30 00:00:00','2025-01-15 00:00:00'),(3,NULL,NULL,NULL,NULL,'DeHashed',NULL,'408-555-3456','789 Fair Oaks Ave','Sunnyvale','CA','94086','1985-11-22 00:00:00','2024-09-20 00:00:00'),(4,'Grace',NULL,'Khan',NULL,'HaveIBeenPwned',NULL,NULL,NULL,NULL,NULL,NULL,'1992-05-05 00:00:00','2025-05-10 00:00:00'),(5,'Henry',NULL,'Zhang',NULL,'BreachAlarm','henry.zhang@sjsu.edu','408-555-5678','654 University Ave','Palo Alto','CA','94301','1991-03-17 00:00:00','2025-02-28 00:00:00'),(6,NULL,NULL,'Anderson',NULL,'DeHashed',NULL,NULL,'987 Main St','Cupertino','CA','95014',NULL,'2024-11-30 00:00:00'),(7,'Jason','Edward','Miller',NULL,'BreachAlarm','jason.miller@sjsu.edu','408-555-7890',NULL,NULL,NULL,NULL,'1993-12-01 00:00:00','2025-03-05 00:00:00'),(8,'Kevin',NULL,'Murphy',NULL,'HaveIBeenPwned','kevin.murphy@sjsu.edu',NULL,NULL,NULL,NULL,NULL,NULL,'2025-06-01 00:00:00'),(9,'Lily',NULL,'Davis',NULL,'DeHashed',NULL,'408-555-9012','159 Broadway','Oakland','CA','94607',NULL,'2025-04-22 00:00:00'),(10,'Michael','Quan','Tan','Mike','BreachAlarm',NULL,'408-555-0123',NULL,'Berkeley','CA',NULL,NULL,'2024-08-19 00:00:00');
/*!40000 ALTER TABLE `Breach_Details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Breach_Report`
--

DROP TABLE IF EXISTS `Breach_Report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Breach_Report` (
  `report_id` int NOT NULL AUTO_INCREMENT,
  `pii_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Breach_Report`
--

LOCK TABLES `Breach_Report` WRITE;
/*!40000 ALTER TABLE `Breach_Report` DISABLE KEYS */;
INSERT INTO `Breach_Report` VALUES (1,1,4),(2,2,5),(3,3,6),(4,4,7),(5,5,8),(6,6,9),(7,7,10),(8,8,18),(9,9,19),(10,10,20);
/*!40000 ALTER TABLE `Breach_Report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Location`
--

DROP TABLE IF EXISTS `Location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Location` (
  `address_id` int NOT NULL,
  `address` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `state` varchar(45) NOT NULL,
  `zip_code` varchar(20) NOT NULL,
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Location`
--

LOCK TABLES `Location` WRITE;
/*!40000 ALTER TABLE `Location` DISABLE KEYS */;
INSERT INTO `Location` VALUES (4,'123 W San Carlos St','San Jose','CA','95126'),(5,'456 Homestead Rd','Santa Clara','CA','95050'),(6,'789 Fair Oaks Ave','Sunnyvale','CA','94086'),(7,'321 Castro St','Mountain View','CA','94041'),(8,'654 University Ave','Palo Alto','CA','94301'),(9,'987 Main St','Cupertino','CA','95014'),(10,'246 N Milpitas Blvd','Milpitas','CA','95035'),(18,'369 El Camino Real','Redwood City','CA','94063'),(19,'159 Broadway','Oakland','CA','94607'),(20,'753 University Ave','Berkeley','CA','94710');
/*!40000 ALTER TABLE `Location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Perform`
--

DROP TABLE IF EXISTS `Perform`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Perform` (
  `action_id` int NOT NULL,
  `log_id` int NOT NULL,
  PRIMARY KEY (`action_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Perform`
--

LOCK TABLES `Perform` WRITE;
/*!40000 ALTER TABLE `Perform` DISABLE KEYS */;
/*!40000 ALTER TABLE `Perform` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Personal_Information`
--

DROP TABLE IF EXISTS `Personal_Information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Personal_Information` (
  `pii_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `middle_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `nickname` varchar(45) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `last_update` datetime DEFAULT NULL,
  `address_id` int DEFAULT NULL,
  PRIMARY KEY (`pii_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Personal_Information`
--

LOCK TABLES `Personal_Information` WRITE;
/*!40000 ALTER TABLE `Personal_Information` DISABLE KEYS */;
INSERT INTO `Personal_Information` VALUES (1,4,'David','Michael','Chen','408-555-1234','Dave','1988-02-14','2025-07-08 00:28:10',4),(2,5,'Emily','Rose','Lopez','408-555-2345','Em','1990-07-30','2025-07-08 00:28:10',5),(3,6,'Frank',NULL,'Liu','408-555-3456',NULL,'1985-11-22','2025-07-08 00:28:10',6),(4,7,'Grace','Anne','Khan','408-555-4567','Gracie','1992-05-05','2025-07-08 00:28:10',7),(5,8,'Henry',NULL,'Zhang','408-555-5678',NULL,'1991-03-17','2025-07-08 00:28:10',8),(6,9,'Isabel','Marie','Anderson','408-555-6789','Izzy','1989-09-09','2025-07-08 00:28:10',9),(7,10,'Jason','Edward','Miller','408-555-7890','Jay','1993-12-01','2025-07-08 00:28:10',10),(8,18,'Kevin','Patrick','Murphy','408-555-8901','Kev','1990-04-02','2025-07-08 00:28:10',18),(9,19,'Lily','Grace','Davis','408-555-9012',NULL,'1994-08-08','2025-07-08 00:28:10',19),(10,20,'Michael','Quan','Tan','408-555-0123','Mike','1987-06-06','2025-07-08 00:28:10',20);
/*!40000 ALTER TABLE `Personal_Information` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Scan`
--

DROP TABLE IF EXISTS `Scan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Scan` (
  `scan_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `pii_id` int NOT NULL,
  `status` enum('Completed','Error') NOT NULL,
  PRIMARY KEY (`scan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Scan`
--

LOCK TABLES `Scan` WRITE;
/*!40000 ALTER TABLE `Scan` DISABLE KEYS */;
INSERT INTO `Scan` VALUES (1,4,1,'Completed'),(2,5,2,'Error'),(3,5,2,'Completed'),(4,6,3,'Completed'),(5,7,4,'Completed'),(6,8,5,'Completed'),(7,9,6,'Completed'),(8,10,7,'Completed'),(9,18,8,'Completed'),(10,19,9,'Completed'),(11,20,10,'Completed');
/*!40000 ALTER TABLE `Scan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Update`
--

DROP TABLE IF EXISTS `Update`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Update` (
  `action_id` int NOT NULL,
  `pii_id` int NOT NULL,
  PRIMARY KEY (`action_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Update`
--

LOCK TABLES `Update` WRITE;
/*!40000 ALTER TABLE `Update` DISABLE KEYS */;
INSERT INTO `Update` VALUES (1,1),(2,2),(3,3),(4,4),(5,5),(6,6),(7,7),(8,8),(9,9),(10,10);
/*!40000 ALTER TABLE `Update` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('Active','Deactivated') NOT NULL DEFAULT 'Active',
  `privilege` enum('Admin','User') NOT NULL DEFAULT 'User',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'toey.lui@sjsu.edu','29nSl3ns04','2025-07-08 00:06:45','Active','Admin'),(2,'patrick.luong@sjsu.edu','3n!*2nfkl3','2025-07-08 00:06:45','Active','Admin'),(3,'victor.le@sjsu.edu','C@2kjf3l!0','2025-07-08 00:06:45','Active','Admin'),(4,'david.chen@sjsu.edu','Dav1dCh3n','2025-07-08 00:06:45','Active','User'),(5,'emily.lopez@sjsu.edu','Emp0WeR2','2025-07-08 00:06:45','Active','User'),(6,'frank.liu@sjsu.edu','Fr4nkl!u','2025-07-08 00:06:45','Active','User'),(7,'grace.khan@sjsu.edu','Gr8c3Kh@n','2025-07-08 00:06:45','Active','User'),(8,'henry.zhang@sjsu.edu','H3nryZh@ng','2025-07-08 00:06:45','Active','User'),(9,'isabel.anderson@sjsu.edu','Is@B3lAn','2025-07-08 00:06:45','Active','User'),(10,'jason.miller@sjsu.edu','Ja5onM!ll','2025-07-08 00:06:45','Active','User'),(11,'alex.johnson@sjsu.edu','Al3xJ0hn!','2025-07-08 00:21:38','Active','Admin'),(12,'brenda.smith@sjsu.edu','Br3nd@$m!','2025-07-08 00:21:38','Active','Admin'),(13,'charles.lee@sjsu.edu','Ch4rl3sL#e','2025-07-08 00:21:38','Active','Admin'),(14,'dorothy.kim@sjsu.edu','D0r0thyK!m','2025-07-08 00:21:38','Active','Admin'),(15,'ethan.ross@sjsu.edu','3th@nR0ss','2025-07-08 00:21:38','Active','Admin'),(16,'fiona.cho@sjsu.edu','F1on@Ch0','2025-07-08 00:21:38','Active','Admin'),(17,'gabriel.park@sjsu.edu','G@br1elP4rk','2025-07-08 00:21:38','Active','Admin'),(18,'kevin.murphy@sjsu.edu','K3v1nM!p','2025-07-08 00:21:38','Active','User'),(19,'lily.davis@sjsu.edu','L1lyDav1s!','2025-07-08 00:21:38','Active','User'),(20,'michael.tan@sjsu.edu','M1chaelT@#','2025-07-08 00:21:38','Active','User');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `has`
--

DROP TABLE IF EXISTS `has`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `has` (
  `report_id` int NOT NULL,
  `breach_flag` enum('True','False') NOT NULL,
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `has`
--

LOCK TABLES `has` WRITE;
/*!40000 ALTER TABLE `has` DISABLE KEYS */;
INSERT INTO `has` VALUES (1,'True'),(2,'True'),(3,'True'),(4,'True'),(5,'True'),(6,'True'),(7,'True'),(8,'True'),(9,'True'),(10,'True');
/*!40000 ALTER TABLE `has` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-08 13:49:50
