ALTER TABLE member ADD COLUMN `gt_cid` varchar(100) DEFAULT NULL COMMENT '个推CID' AFTER `property_id`;
ALTER TABLE member ADD COLUMN `ry_token` varchar(100) DEFAULT NULL COMMENT '个推CID' AFTER `gt_cid`;