CREATE TABLE `t_report_game_day_down` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `g_id` int(11) DEFAULT NULL,
  `bucket` varchar(32) DEFAULT '0' COMMENT '云空间名称',
  `prefix` varchar(32) DEFAULT '0' COMMENT '文件前缀名',
  `file_name` varchar(128) DEFAULT '0' COMMENT '文件名',
  `date` date NOT NULL DEFAULT '0000-00-00' COMMENT '更新时间',
  `day_counts_total` int(11) DEFAULT '0' COMMENT '日下载增量',
  `day_counts_incr` int(11) DEFAULT '0' COMMENT '日下载增量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `identity_file_idx` (`bucket`,`prefix`,`file_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

