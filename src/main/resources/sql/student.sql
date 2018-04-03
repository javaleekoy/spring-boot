/*
Navicat MySQL Data Transfer

Source Server         : location
Source Server Version : 50719
Source Host           : 192.168.136.130:13307
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-04-03 17:17:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `stuName` varchar(255) DEFAULT NULL COMMENT '学生姓名',
  `classId` int(11) DEFAULT NULL COMMENT '班级Id',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='studetn';

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '张三', '22', '2017-08-21 15:52:17', '2017-08-21 15:52:19');
INSERT INTO `student` VALUES ('7', '李四', '22', '2017-09-18 09:44:56', '2017-09-18 09:44:56');
INSERT INTO `student` VALUES ('8', '王五', '11', '2017-09-19 03:08:46', '2017-09-19 03:08:46');
