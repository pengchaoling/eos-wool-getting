DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pk` varchar(100) DEFAULT NULL COMMENT '私钥',
  `accountname` varchar(50) DEFAULT NULL COMMENT '账户名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `account_temp`;
CREATE TABLE `account_temp` (
  `id` int(11) NOT NULL DEFAULT '0',
  `pk` varchar(100) DEFAULT NULL,
  `accountname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

