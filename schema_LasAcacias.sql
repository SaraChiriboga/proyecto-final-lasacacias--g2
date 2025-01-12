-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: las_acacias_sistema
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `administrador`
--

DROP TABLE IF EXISTS `administrador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administrador` (
  `idUser` int NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `contrasena` varchar(45) DEFAULT NULL,
  `CI` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrador`
--

LOCK TABLES `administrador` WRITE;
/*!40000 ALTER TABLE `administrador` DISABLE KEYS */;
INSERT INTO `administrador` VALUES (202,'Domenica Teran','002','1709059511'),(222,'Martina','222','1752360360'),(404,'Luis','004','1709045322'),(505,'Sara','123','1523847733');
/*!40000 ALTER TABLE `administrador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `caballo`
--

DROP TABLE IF EXISTS `caballo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `caballo` (
  `idcaballo` int NOT NULL,
  `edad` int NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `raza` varchar(45) DEFAULT NULL,
  `sexo` varchar(45) DEFAULT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  `estadoSalud` varchar(45) DEFAULT NULL,
  `estadoEntrenamiento` varchar(45) DEFAULT NULL,
  `peso` double DEFAULT NULL,
  `valorComercial` double DEFAULT NULL,
  `campo` varchar(45) DEFAULT NULL,
  `alimentacion` double DEFAULT NULL,
  UNIQUE KEY `idcaballo_UNIQUE` (`idcaballo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `caballo`
--

LOCK TABLES `caballo` WRITE;
/*!40000 ALTER TABLE `caballo` DISABLE KEYS */;
INSERT INTO `caballo` VALUES (1,1,'lola','arabe','H','Blanca','Bueno','---',230,10000,'Principal',3.4499999999999997),(8,15,'Gaia','Arabe','H','MUERTA','---','BUENO',400,3000,'Principal',0),(45,15,'Percheron','Arabe','M','muerto','---','---',600,9000,'secundario',0),(67,1,'pablo','arabe','M','cafe','Excelente','bueno',300,6000,'principal',0),(87,9,'franchesco','arabe','M','NO','---','MALO',900,12000,'1ERO',27),(90,3,'maria','arab','H','no','---','principiante',300,8000,'secundario',0),(109,1,'aria','arabe','H','cafe con mancha blanca en el ojo derecho','excelente','---',239,10000,'Principal',3.585),(122,1,'daya','arabe','H','---','---','---',234,2000,'acacias',3.51),(123,10,'Agata','Arabe','H','no','excelente','avanzado',450,4000,'principal',0),(169,14,'fabio','arabe','M','---','---','---',800,2000,'Principal',24),(190,8,'MARIO','ARABE','M','---','---','---',732,2000,'acacias',21.96),(708,8,'marlon','arabe','M','---','---','---',800,10000,'primario',24),(866,21,'thanos','arabe','M','---','---','---',900,2300,'Principal',27),(911,6,'Simon','Arabe','M','Muerto','Excelente','---',800,23000,'Principal',24),(1000,9,'troy','arabe','M','MUERTO','---','avanzado',800,9000,'Secundario',24);
/*!40000 ALTER TABLE `caballo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `criadero`
--

DROP TABLE IF EXISTS `criadero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `criadero` (
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `criadero`
--

LOCK TABLES `criadero` WRITE;
/*!40000 ALTER TABLE `criadero` DISABLE KEYS */;
INSERT INTO `criadero` VALUES ('Principal','Se encuentran los mejores caballos de la hacienda'),('Secundario','Campo para entrenamientos');
/*!40000 ALTER TABLE `criadero` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historialmed`
--

DROP TABLE IF EXISTS `historialmed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historialmed` (
  `idCaballo` int NOT NULL,
  `vacuna` varchar(45) DEFAULT NULL,
  `desparacitacion` varchar(45) DEFAULT NULL,
  `enfermedad` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historialmed`
--

LOCK TABLES `historialmed` WRITE;
/*!40000 ALTER TABLE `historialmed` DISABLE KEYS */;
INSERT INTO `historialmed` VALUES (67,'idk','si','covid'),(100,'covid','no','cancer'),(28,'NO','NO','NO'),(1,'si','si','---');
/*!40000 ALTER TABLE `historialmed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trabajador`
--

DROP TABLE IF EXISTS `trabajador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trabajador` (
  `idUser` int NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `contrasena` varchar(45) DEFAULT NULL,
  `CI` int DEFAULT NULL,
  `cargo` varchar(45) DEFAULT NULL,
  `salario` double DEFAULT NULL,
  `turno` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trabajador`
--

LOCK TABLES `trabajador` WRITE;
/*!40000 ALTER TABLE `trabajador` DISABLE KEYS */;
INSERT INTO `trabajador` VALUES (823,'Manolo','823',1316109626,'cuidador',700,'Diurno');
/*!40000 ALTER TABLE `trabajador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `veterinaria`
--

DROP TABLE IF EXISTS `veterinaria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `veterinaria` (
  `idUser` int NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `contrasena` varchar(45) DEFAULT NULL,
  `CI` varchar(45) DEFAULT NULL,
  `registroSenecyt` varchar(45) DEFAULT NULL,
  `especializacion` varchar(45) DEFAULT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `veterinaria`
--

LOCK TABLES `veterinaria` WRITE;
/*!40000 ALTER TABLE `veterinaria` DISABLE KEYS */;
INSERT INTO `veterinaria` VALUES (100,'Marco','100','1752333456','1005-04-491936','Reproducción Equina','0968744435'),(124,'Juan','033','1234928333','123-123-44555','Cirugía','937229123'),(200,'Camila','200','172344511','1051-2024-2874702','Cirugía Equina','098233423'),(300,'Gustavo','300','1734823845','1051-2023-233422','Oftalmología Equina','0934571279');
/*!40000 ALTER TABLE `veterinaria` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-12 15:22:23
