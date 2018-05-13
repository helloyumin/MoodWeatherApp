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
-- Table structure for table `wordtable`
--

DROP TABLE IF EXISTS `wordtable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wordtable` (
  `Word` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wordtable`
--

LOCK TABLES `wordtable` WRITE;
/*!40000 ALTER TABLE `wordtable` DISABLE KEYS */;
INSERT INTO `wordtable` VALUES ('힘이 들땐 걸어도 돼 / 조금 늦게 걸어도 돼 / 아직 먼 길을 가야해 / 숨이 찰땐 걸어도 돼'),('인생은 겸손에 대한 오랜 수업이다'),('당신이 얼마나 스트레스 받고 있는지에 집중하지 말고 얼마나 축복 받았는지에 집중해라'),('인생은 집을 향한 여행이다'),('사람은 행복하기로 마음먹은 만큼 행복하다'),('1분 1초마다 인생을 바꿀 수 있는 기회가 온다'),('원하는 것을 얻지 못하는 것이 때로는 행운이라는 것을 기억해라'),('내일의 모든 꽃은 오늘의 씨앗에 근거한 것이다'),('길을 걷다가 돌을 보면 약자는 그것을 걸림돌이라 하고, 강자는 그것을 디딤돌이라고 한다'),('바람이 도와주지 않으면 노에 의지해라'),('당신이 있는 곳에서 당신이 가진 것을 가지고 당신이 할 수 있는 것을 해라'),('이 세상에서 가장 행복한 사람은 일하는 사람, 사랑하는 사람, 희망이 있는 사람이다'),('항상 쾌활함을 유지하는 비결은 사소한 일에 얽매이지 않으면서 운명이 가져다주는 작은 기쁨에 감사하는 것이다'),('명장면의 연출가는 세월이다'),('내 기분은 내가 정해 오늘 나는 \'행복\'으로 할래'),('행복의 열쇠 중 하나는 나쁜 기억이다.');
/*!40000 ALTER TABLE `wordtable` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-13 14:18:55
