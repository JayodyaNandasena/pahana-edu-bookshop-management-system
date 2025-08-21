-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: pahana_edu_db
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `customer_id` bigint NOT NULL,
  `cashier_id` bigint NOT NULL,
  `bill_date` date NOT NULL,
  `bill_time` time NOT NULL,
  `total` decimal(12,2) NOT NULL,
  `code` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `bill_code_unique` (`code`),
  KEY `idx_bill_customer` (`customer_id`),
  KEY `idx_bill_user` (`cashier_id`),
  CONSTRAINT `fk_bill_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_bill_user` FOREIGN KEY (`cashier_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_total_nonnegative` CHECK ((`total` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (1,1,1,'2025-06-01','10:00:00',1600.00,NULL),(2,1,2,'2025-07-02','13:15:00',550.00,NULL),(3,3,3,'2025-07-03','12:30:00',3200.00,NULL),(4,4,4,'2025-07-04','14:00:00',1700.00,NULL),(5,5,5,'2025-07-05','15:45:00',680.00,NULL),(6,20,1,'2025-07-09','16:12:33',8000.00,NULL),(7,20,1,'2025-08-09','16:17:02',8000.00,NULL),(8,20,1,'2025-08-09','16:47:50',9400.00,'BILL-20250908-164750-0008'),(12,20,1,'2025-08-09','17:38:43',21200.00,'BILL-20250908-173843-0012'),(13,20,1,'2025-08-09','23:24:30',5500.00,'BILL-20250908-232430-0013'),(14,20,1,'2025-08-10','00:57:43',5360.00,'BILL-20251008-005743-0014'),(15,20,1,'2025-08-10','23:16:50',6800.00,'BILL-20251008-231650-0015'),(16,20,1,'2025-08-10','23:27:51',4350.00,'BILL-20251008-232751-0016'),(17,21,1,'2025-08-10','23:30:03',3500.00,'BILL-20251008-233003-0017'),(18,20,1,'2025-08-11','11:23:21',3500.00,'BILL-20251108-112321-0018'),(19,20,1,'2025-08-11','13:01:07',8000.00,'BILL-20251108-130107-0019'),(20,20,1,'2025-08-16','00:26:36',3500.00,'BILL-20250816-002636-0020'),(21,20,2,'2025-08-18','00:21:11',120.00,'BILL-20250818-002111-0021'),(22,1,1,'2025-08-20','15:57:46',270.00,'BILL-20250820-155746-0022'),(23,1,2,'2025-08-20','21:57:10',1020.00,'BILL-20250820-215710-0023');
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bill_item`
--

DROP TABLE IF EXISTS `bill_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bill_id` bigint NOT NULL,
  `item_id` bigint NOT NULL,
  `quantity` int NOT NULL,
  `unit_price` decimal(12,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_billitem` (`bill_id`,`item_id`),
  KEY `idx_billitem_bill` (`bill_id`),
  KEY `idx_billitem_item` (`item_id`),
  CONSTRAINT `fk_billitem_bill` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_billitem_item` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_quantity_positive` CHECK ((`quantity` > 0)),
  CONSTRAINT `chk_unit_price_nonnegative` CHECK ((`unit_price` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill_item`
--

LOCK TABLES `bill_item` WRITE;
/*!40000 ALTER TABLE `bill_item` DISABLE KEYS */;
INSERT INTO `bill_item` VALUES (1,1,1,1,1500.00),(2,1,5,2,50.00),(3,2,4,2,250.00),(4,2,5,1,50.00),(5,3,3,1,3200.00),(6,4,12,1,1500.00),(7,4,11,1,200.00),(8,5,13,1,500.00),(9,5,15,1,180.00),(10,6,1,2,1500.00),(11,6,2,2,2500.00),(12,7,1,2,1500.00),(13,7,2,2,2500.00),(14,8,1,2,1500.00),(15,8,3,2,3200.00),(21,12,13,2,500.00),(22,12,12,5,1500.00),(23,12,15,50,180.00),(24,12,5,50,50.00),(25,12,6,10,120.00),(26,13,2,2,2500.00),(27,13,4,2,250.00),(28,14,2,2,2500.00),(29,14,6,3,120.00),(30,15,14,2,350.00),(31,15,13,5,500.00),(32,15,15,20,180.00),(33,16,5,5,50.00),(34,16,6,5,120.00),(35,16,7,1,3500.00),(36,17,7,1,3500.00),(37,18,7,1,3500.00),(38,19,22,1,7000.00),(39,19,24,2,500.00),(40,20,7,1,3500.00),(41,21,6,1,120.00),(42,22,6,1,120.00),(43,22,5,3,50.00),(44,23,6,1,120.00),(45,23,11,3,300.00);
/*!40000 ALTER TABLE `bill_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Books'),(3,'Educational Material'),(4,'Gift Items'),(5,'Miscellaneous'),(2,'Stationery');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `address` text,
  `units_consumed` int DEFAULT '0',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'John','Doe','john@example.com','0700000000','No.03, Flower Road, Kandy',23,1),(2,'Jane','Smith','jane@example.com','0987654321','456 Oak St',20,1),(3,'Bob','Brown','bobbrown@gmail.com','0774567890','789 Pine St',5,1),(4,'Alice','Johnson','alice@example.com','0774567899','321 Birch St',0,0),(5,'Eve','Davis','eve@example.com','5554443333','654 Cedar St',10,1),(20,'Jayodya','Nandasena','jayodyanandasena@gmail.com','0767734768','123 Main Street, Kandy',198,1),(21,'Emily','Watson','emilywatson@example.com','0771232367','123 Main Street, Kandy',1,1),(33,'John','Doe','johndoe3@gmail.com','0774567891','123 Main Street',0,1);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `unit_price` decimal(12,2) NOT NULL,
  `quantity_available` int NOT NULL DEFAULT '0',
  `category_id` bigint NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_item_name_category` (`name`,`category_id`),
  KEY `idx_item_category` (`category_id`),
  CONSTRAINT `fk_item_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,'The Great Gatsby',1500.00,100,1,0),(2,'Harry Potter and the Philosopher\'s Stone',2500.00,50,1,0),(3,'Sherlock Holmes Collection',3200.00,9,1,0),(4,'A5 Spiral Notebook',250.00,58,2,0),(5,'Ballpoint Pen (Blue)',50.00,142,2,0),(6,'Pencil Pack (HB)',120.00,79,2,0),(7,'Oxford English Dictionary',3500.00,8,3,0),(8,'Science Experiment Kit',4500.00,0,3,0),(9,'Mathematics Practice Workbook',800.00,25,3,0),(10,'Greeting Card',200.00,40,4,1),(11,'Bookmark Set',300.00,27,4,0),(12,'Book Lamp',1500.00,10,4,0),(13,'Reusable Tote Bag',500.00,13,5,0),(14,'Book Cover Plastic Roll',350.00,48,5,0),(15,'Sticky Notes',180.00,1430,5,0),(22,'Ballpoint Pen (Black)',7000.00,49,2,0),(24,'File folder set',500.00,98,2,0);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password_hash` text NOT NULL,
  `user_type` enum('ADMIN','STAFF') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Admin One','admin1','$2a$12$Kf60hdY7NbanJbLMpkZqneoF/a7jqVah3dHUijfgufQQwUGQ6NAL.','ADMIN'),(2,'Cashier A','staff1','$2a$12$v99u.lwmygRLCmiT4XyvJ.OiBSWHtCw.rmdcur7BbHtbY2VCH978C','STAFF'),(3,'Staff Member','staffm','$2a$12$SuI9BMTFKEs74CqnRLWOh.OPct6IlpIsL4ur509McoFHAtbMvj3ei','STAFF'),(4,'Cashier B','cashierb','$2a$12$SqSjDaiLw4PAkSSqmj6xBO.7XPRjRcC9gRlF6RdZg2kpMhGd0ckxa','STAFF'),(5,'Admin Two','admin2','$2a$12$aql0kemGYPvy0WkSTwDweOsjT5n2wFZK9yvpemN1TJmy4BqyAuhCG','ADMIN'),(11,'Test User','testuser','$2a$12$lIf1P30cLQetubU/5ZxCLOFVrpQln7LnAAZl7gpHigI5THgxBc7Tm','ADMIN');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'pahana_edu_db'
--
/*!50003 DROP FUNCTION IF EXISTS `get_average_bill` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `get_average_bill`() RETURNS decimal(12,2)
    DETERMINISTIC
RETURN (SELECT AVG(total) FROM bill) ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `get_total_bill` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `get_total_bill`() RETURNS decimal(12,2)
    DETERMINISTIC
RETURN (SELECT SUM(total) FROM bill) ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-21 13:52:50
