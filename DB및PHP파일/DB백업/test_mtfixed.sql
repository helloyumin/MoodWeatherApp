-- MySQL dump 10.13  Distrib 5.7.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: test
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `mtfixed`
--

DROP TABLE IF EXISTS `mtfixed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mtfixed` (
  `Num` int(11) DEFAULT NULL,
  `Music` varchar(500) DEFAULT NULL,
  `Video` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mtfixed`
--

LOCK TABLES `mtfixed` WRITE;
/*!40000 ALTER TABLE `mtfixed` DISABLE KEYS */;
INSERT INTO `mtfixed` VALUES (1,'Left Right Left - Charlie Puth','z-eg-bBCQJM'),(1,'Feels (Feat. Pharrell Williams, Katy Perry & Big Sean) - Calvin Harris','ozv4q2ov3Mk'),(1,'I Feel It Coming (Feat. Daft Punk) - The Weeknd','qFLhGq0060w'),(1,'Drunk In The Morning - Lukas Graham','yvHqenLpUU'),(1,'Thunder - Imagine Dragons','4AqjqOqNrjw'),(1,'This Is How We do - Katy Perry','7RMQksXpQSk'),(1,'Love - Lana Del Ray','3-NTv0CdFCk'),(1,'Finesse (Remix) (Feat. Cardi B) - Bruno Mars','LsoLEjrDogU'),(1,'No Tears Left To Cry - Ariana Grande','ffxKSjUwKdU'),(2,'A Thousand Years - Christina Perri','rtOvBOTyX00'),(2,'Day 1 - HONNE','hWOB5QYcmh0'),(2,'Shake If Off - Taylor Swift','nfWlot6h_JM'),(2,'Overload (Feat. Miguel) - John Legend','x7A6e9pUtq0'),(2,'Beautiful Lies - Birdy','ckj0m0Pv0Sc'),(2,'Mrs. Cold - Kings Of Convenience','UBtjik6uYrY'),(2,'Nice And Slow - Max Frost','nix1nEZhpZ4'),(2,'Viva La Vida - Coldplay','zOQ4ld6NsXE'),(2,'Green Light - Lorde','dMK_npDG12Q'),(2,'Lost In Japan - Shawn Mendes','ycy30LIbq4w'),(2,'Havana - Camila Cabello (Feat. Young Thug)','BQ0mxQXmLsk'),(3,'Photograph - Ed Sheeran','nSDgHBxUbVQ'),(3,'Crowded Places - Banks','t99ZhG5LEVk'),(3,'One Call Away - Charlie Puth','BxuY9FET9Y4'),(3,'Babe - Sugarland (Feat. Taylor Swift)','fNVUTgd4pio'),(3,'Viva La Vida - Coldplay','zOQ4ld6NsXE'),(3,'Beautiful Lies - Birdy','ckj0m0Pv0Sc'),(3,'Genius - LSD (Feat. Labrinth, Sia, Diplo)','HhoATZ1Imtw'),(3,'Be Be Your Love - Rachael Yamagata','4Uyli38PzT8'),(3,'New Rules - Dua Lipa','k2qgadSvNyU'),(3,'Too Good At Goodbyes - Sam Smith','J_ub7Etch2U'),(4,'Young And Beautiful - Lana Del Rey','o_1aF54DO60'),(4,'Open Hands (Feat. Trent Dabbs) - Ingrid Michaelson','9sLW5hT6NwE'),(4,'Mahattan - Sara Bareilles','NlxxSAnK6Do'),(4,'The Scientist - Coldplay','Y9idMMQ4'),(4,'답답한 새벽 - 스웨덴세탁소','sohFdOgOr_0'),(4,'떠나자 - 에피톤 프로젝트','5gt0ELLFdoM'),(4,'I hate u, I love u (Feat. Olivia o`brien) (Albem Ver.) - gnash','BiQIc7fG9pA'),(4,'Sentimental Scene - 센티멘탈 시너리(Sentimental Scenery)','LGcbBiA_kTU'),(4,'Cayman Islands - King Of Convenience','c-ppARtcQfo'),(4,'꼭 이만큼만 - Casker','F2zV_LEOJc8');
/*!40000 ALTER TABLE `mtfixed` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-13 14:18:54
