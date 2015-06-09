CREATE TABLE `t_report_game_day_down` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `g_id` int(11) DEFAULT NULL,
  `bucket` varchar(32) DEFAULT '0' COMMENT '�ƿռ�����',
  `prefix` varchar(32) DEFAULT '0' COMMENT '�ļ�ǰ׺��',
  `file_name` varchar(128) DEFAULT '0' COMMENT '�ļ���',
  `date` date NOT NULL DEFAULT '0000-00-00' COMMENT '����ʱ��',
  `day_counts_total` int(11) DEFAULT '0' COMMENT '����������',
  `day_counts_incr` int(11) DEFAULT '0' COMMENT '����������',
  PRIMARY KEY (`id`),
  UNIQUE KEY `identity_file_idx` (`bucket`,`prefix`,`file_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

