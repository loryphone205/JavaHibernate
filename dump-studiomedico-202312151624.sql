-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: studiomedico
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `medico`
--

DROP TABLE IF EXISTS `medico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medico` (
  `idMedico` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `cognome` varchar(255) DEFAULT NULL,
  `specializzazione` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idMedico`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medico`
--

LOCK TABLES `medico` WRITE;
/*!40000 ALTER TABLE `medico` DISABLE KEYS */;
INSERT INTO `medico` VALUES (1,'Serena','Dini','Psicologia'),(2,'Giuseppe','Mursia','Ematologo'),(3,'Daniele','Monaco','Ragazzi....'),(4,'Pierluigi','???','Linux > Windows');
/*!40000 ALTER TABLE `medico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paziente`
--

DROP TABLE IF EXISTS `paziente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paziente` (
  `idPaziente` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `cognome` varchar(255) DEFAULT NULL,
  `dataNascita` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`idPaziente`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paziente`
--

LOCK TABLES `paziente` WRITE;
/*!40000 ALTER TABLE `paziente` DISABLE KEYS */;
INSERT INTO `paziente` VALUES (1,'Lorenzo','Vaccaro','24/10/2000'),(2,'Lenny','Rusciano','00/00/2003'),(3,'Alessandro','Peluso','00/00/1996'),(4,'Simone Maria','Luongo','31/12/9999'),(5,'Salvatore','Del Piano','31/12/8888'),(6,'Christian','Paciolla','31/12/7777'),(7,'Diego','Tursi','31/12/2000'),(8,'Claudio','Manganiello','31/12/2003'),(9,'Gemma','Ragi','31/12/4444'),(10,'Giacomo','Piccolo','31/12/3333'),(11,'Antonio','NonLoSoSorry','31/12/2222');
/*!40000 ALTER TABLE `paziente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visita`
--

DROP TABLE IF EXISTS `visita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visita` (
  `idVisita` int NOT NULL AUTO_INCREMENT,
  `tipoVisita` varchar(255) DEFAULT NULL,
  `idMedico` int DEFAULT NULL,
  `idPaziente` int DEFAULT NULL,
  PRIMARY KEY (`idVisita`),
  KEY `FK_idm` (`idMedico`),
  KEY `FK_idp` (`idPaziente`),
  CONSTRAINT `FK_idm` FOREIGN KEY (`idMedico`) REFERENCES `medico` (`idMedico`) ON DELETE CASCADE,
  CONSTRAINT `FK_idp` FOREIGN KEY (`idPaziente`) REFERENCES `paziente` (`idPaziente`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visita`
--

LOCK TABLES `visita` WRITE;
/*!40000 ALTER TABLE `visita` DISABLE KEYS */;
INSERT INTO `visita` VALUES (1,'Analisi Ematocrito',2,1),(2,'Sessione Di Analisi',1,3),(3,'Sessione Di Analisi',1,6),(4,'Sessione Di Analisi',1,9),(5,'Sessione Di Analisi',1,10),(6,'Sessione Di Analisi',1,4),(7,'Sessione Di Analisi',1,5),(8,'Ragazzi....',3,1),(9,'Ragazzi....',3,2),(10,'Ragazzi....',3,3),(11,'Ragazzi....',3,4),(12,'Ragazzi....',3,5),(13,'Ragazzi....',3,6),(14,'Ragazzi....',3,7),(15,'Ragazzi....',3,8),(16,'Ragazzi....',3,9),(17,'Ragazzi....',3,10),(18,'Ragazzi....',3,11);
/*!40000 ALTER TABLE `visita` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'studiomedico'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-15 16:24:10
