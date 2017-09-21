alter table ad add column  `file_size` varchar(10)  COMMENT '视频大小' after media_url;

alter table ad add column  `file_time` varchar(10)  COMMENT '视频时长' after file_size;