

CREATE TABLE `class` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `className` varchar(255) DEFAULT NULL COMMENT '班级名称',
  `studentCount` int(11) DEFAULT NULL COMMENT '学生数量',
  `stauts` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0.正常，1.删除',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `craeteTime` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='class';



CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `stuName` varchar(255) DEFAULT NULL COMMENT '学生姓名',
  `classId` int(11) DEFAULT NULL COMMENT '班级Id',
  `token` varchar(255) NOT NULL COMMENT 'auth-token',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='student';
