-- MySQL dump 10.13  Distrib 5.1.44, for Win32 (ia32)
--
-- Host: localhost    Database: tu_share
-- ------------------------------------------------------
-- Server version	5.1.44-community

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
-- Table structure for table `sm_oid`
--

DROP TABLE IF EXISTS `sm_oid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sm_oid` (
  `SEED` varchar(5) NOT NULL COMMENT '种子',
  PRIMARY KEY (`SEED`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='oid生成';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sm_oid`
--

LOCK TABLES `sm_oid` WRITE;
/*!40000 ALTER TABLE `sm_oid` DISABLE KEYS */;
INSERT INTO `sm_oid` VALUES ('11426');
/*!40000 ALTER TABLE `sm_oid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_about`
--

DROP TABLE IF EXISTS `tb_about`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_about` (
  `PK_AbOUT` char(20) NOT NULL COMMENT '主键',
  `CONTENT` varchar(2408) NOT NULL COMMENT '关于我们',
  `PHONE` varchar(128) DEFAULT NULL,
  `WEB_URL` varchar(128) NOT NULL COMMENT '网址',
  PRIMARY KEY (`PK_AbOUT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='关于我们';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_about`
--

LOCK TABLES `tb_about` WRITE;
/*!40000 ALTER TABLE `tb_about` DISABLE KEYS */;
INSERT INTO `tb_about` VALUES ('1001','图片分享','15003888092','www.baidu.com');
/*!40000 ALTER TABLE `tb_about` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_member`
--

DROP TABLE IF EXISTS `tb_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_member` (
  `PK_MEMBER` char(20) NOT NULL COMMENT '主键',
  `NAME` varchar(128) NOT NULL COMMENT '账户',
  `NICK_NAME` varchar(128) DEFAULT NULL COMMENT '昵称',
  `PASSWORD` varchar(128) NOT NULL COMMENT '密码',
  `LOGO_PATH` varchar(128) DEFAULT NULL COMMENT '头像图片地址',
  `TS` char(19) DEFAULT NULL,
  PRIMARY KEY (`PK_MEMBER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_member`
--

LOCK TABLES `tb_member` WRITE;
/*!40000 ALTER TABLE `tb_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_pic`
--

DROP TABLE IF EXISTS `tb_pic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_pic` (
  `PK_PIC` char(20) NOT NULL COMMENT '主键',
  `PK_MEMBER` char(20) NOT NULL COMMENT '会员主键',
  `NICK_NAME` varchar(128) DEFAULT NULL,
  `PIC_PATH` varchar(128) NOT NULL COMMENT '图片地址',
  `PIC_NAME` varchar(128) DEFAULT NULL COMMENT '图片名字',
  `PIC_MEMO` varchar(1024) DEFAULT NULL COMMENT '图片描述',
  `TS` char(19) DEFAULT NULL,
  PRIMARY KEY (`PK_PIC`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_pic`
--

LOCK TABLES `tb_pic` WRITE;
/*!40000 ALTER TABLE `tb_pic` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_pic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_pic_collect`
--

DROP TABLE IF EXISTS `tb_pic_collect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_pic_collect` (
  `PK_PIC_COLLECT` char(20) NOT NULL COMMENT '主键',
  `PK_MEMBER` char(20) NOT NULL COMMENT '会员主键',
  `PK_PIC` char(20) NOT NULL COMMENT '图片主键',
  `TS` char(19) DEFAULT NULL,
  PRIMARY KEY (`PK_PIC_COLLECT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片收藏表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_pic_collect`
--

LOCK TABLES `tb_pic_collect` WRITE;
/*!40000 ALTER TABLE `tb_pic_collect` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_pic_collect` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_pic_comment`
--

DROP TABLE IF EXISTS `tb_pic_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_pic_comment` (
  `PK_PIC_COMMENT` char(20) NOT NULL COMMENT '主键',
  `PK_MEMBER` char(20) NOT NULL COMMENT '会员主键',
  `PK_PIC` char(20) NOT NULL COMMENT '图片主键',
  `PIC_MEMO` varchar(1024) NOT NULL COMMENT '评论内容',
  `TS` char(19) DEFAULT NULL,
  PRIMARY KEY (`PK_PIC_COMMENT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_pic_comment`
--

LOCK TABLES `tb_pic_comment` WRITE;
/*!40000 ALTER TABLE `tb_pic_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_pic_comment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-08-27 12:07:33
