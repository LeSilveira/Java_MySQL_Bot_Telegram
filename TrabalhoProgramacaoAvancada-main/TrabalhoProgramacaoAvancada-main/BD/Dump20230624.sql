-- MySQL dump 10.13  Distrib 8.0.33, for Linux (x86_64)
--
-- Host: localhost    Database: lancheria
-- ------------------------------------------------------
-- Server version	8.0.33-0ubuntu0.22.04.2

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
-- Table structure for table `Categoria`
--

DROP TABLE IF EXISTS `Categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Categoria` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Categoria`
--

LOCK TABLES `Categoria` WRITE;
/*!40000 ALTER TABLE `Categoria` DISABLE KEYS */;
INSERT INTO `Categoria` VALUES (5,'Bebidas Geladas'),(6,'Cafés'),(7,'Chás'),(8,'Sucos'),(9,'Pastéis'),(10,'Sanduíches'),(11,'Pratos');
/*!40000 ALTER TABLE `Categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cliente`
--

DROP TABLE IF EXISTS `Cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Cliente` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1959549053 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cliente`
--

LOCK TABLES `Cliente` WRITE;
/*!40000 ALTER TABLE `Cliente` DISABLE KEYS */;
INSERT INTO `Cliente` VALUES (1,'Ana Paula Souza'),(2,'Pedro Fonseca'),(3,'Paola de Andrade'),(1959549052,'Maria null');
/*!40000 ALTER TABLE `Cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Pedido`
--

DROP TABLE IF EXISTS `Pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Pedido` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cliente_id` int NOT NULL,
  `data` timestamp NOT NULL,
  `finalizado` tinyint NOT NULL,
  `entregue` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cliente_id_idx` (`cliente_id`),
  CONSTRAINT `fk_cliente_id` FOREIGN KEY (`cliente_id`) REFERENCES `Cliente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pedido`
--

LOCK TABLES `Pedido` WRITE;
/*!40000 ALTER TABLE `Pedido` DISABLE KEYS */;
INSERT INTO `Pedido` VALUES (1,3,'2023-06-24 20:40:01',1,0),(2,1,'2023-06-24 20:40:08',1,0),(3,2,'2023-06-24 20:39:49',1,0);
/*!40000 ALTER TABLE `Pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PedidoItem`
--

DROP TABLE IF EXISTS `PedidoItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PedidoItem` (
  `pedido_id` int NOT NULL,
  `produto_id` int NOT NULL,
  `quantidade` int NOT NULL,
  `preco` double NOT NULL,
  `observacao` varchar(200) DEFAULT NULL,
  KEY `fk_pedido_id_idx` (`pedido_id`),
  KEY `fk_produto_id_idx` (`produto_id`),
  CONSTRAINT `fk_pedido_id` FOREIGN KEY (`pedido_id`) REFERENCES `Pedido` (`id`),
  CONSTRAINT `fk_produto_id` FOREIGN KEY (`produto_id`) REFERENCES `Produto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PedidoItem`
--

LOCK TABLES `PedidoItem` WRITE;
/*!40000 ALTER TABLE `PedidoItem` DISABLE KEYS */;
INSERT INTO `PedidoItem` VALUES (1,10,2,7.45,'com açucar'),(1,13,3,4.67,'com gelo, copo plástico, limão e canudo'),(2,7,5,4.9,'bem gelada'),(2,12,2,6.45,'com gelo, copo plástico, limão'),(2,13,1,4.67,'com gelo, copo plástico, limão'),(3,7,5,4.9,'bem gelada'),(3,12,2,6.45,'com gelo, copo plástico, limão'),(3,13,1,4.67,'com gelo, copo plástico, limão'),(3,8,3,42.9,'sem açucar');
/*!40000 ALTER TABLE `PedidoItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Produto`
--

DROP TABLE IF EXISTS `Produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Produto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `categoria_id` int NOT NULL,
  `descricao` varchar(45) NOT NULL,
  `preco` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_categoria_id_idx` (`categoria_id`),
  CONSTRAINT `fk_categoria_id` FOREIGN KEY (`categoria_id`) REFERENCES `Categoria` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Produto`
--

LOCK TABLES `Produto` WRITE;
/*!40000 ALTER TABLE `Produto` DISABLE KEYS */;
INSERT INTO `Produto` VALUES (7,5,'Bud lata 250 ml',4.9),(8,6,'Expresso 100 ml',2.9),(10,6,'cappucino 200 ml',7.45),(11,6,'mokacino 200 ml',8.9),(12,5,'Refri guaraná Fruki 600 ml',6.45),(13,5,'Refri coca cola 200 ml',4.67),(14,7,'Chá de Hortelã',3.67),(16,9,'Pastel de Frango',6.9),(17,9,'Pastel de Carne 200 g',7.06),(18,10,'Sanduíche de salmão',8.09);
/*!40000 ALTER TABLE `Produto` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-24 23:15:24
