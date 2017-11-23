use eshop_ad;
CREATE TABLE `ad_rate_setting` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `game_time` int(5) unsigned NOT NULL COMMENT '游戏时长（单位秒）',
  `rate` int(5) NOT NULL COMMENT '对应命中率0~100',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='咻一咻命中率配置表';