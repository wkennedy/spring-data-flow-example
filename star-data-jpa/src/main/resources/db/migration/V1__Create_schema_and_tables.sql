--
-- Table structure for table `car_fact`
--

DROP TABLE IF EXISTS `car_fact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `car_fact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `engine` int(11) NOT NULL,
  `make` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `car_fact_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car_fact`
--

LOCK TABLES `car_fact` WRITE;
/*!40000 ALTER TABLE `car_fact` DISABLE KEYS */;
/*!40000 ALTER TABLE `car_fact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `engine_dim`
--

DROP TABLE IF EXISTS `engine_dim`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `engine_dim` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) NOT NULL,
  `displacement` varchar(32) NOT NULL,
  `fuel` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `engine_dim_code_index` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `engine_dim`
--

LOCK TABLES `engine_dim` WRITE;
/*!40000 ALTER TABLE `engine_dim` DISABLE KEYS */;
INSERT INTO `engine_dim` VALUES (1,'SR20DE','2.0 litres','gasoline'),(2,'N54','3.0 litres','gasoline');
/*!40000 ALTER TABLE `engine_dim` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `make_dim`
--

DROP TABLE IF EXISTS `make_dim`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `make_dim` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `country` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `make_dim_id_uindex` (`id`),
  KEY `make_dim_name_index` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `make_dim`
--

LOCK TABLES `make_dim` WRITE;
/*!40000 ALTER TABLE `make_dim` DISABLE KEYS */;
INSERT INTO `make_dim` VALUES (1,'Ford','USA');
/*!40000 ALTER TABLE `make_dim` ENABLE KEYS */;
UNLOCK TABLES;