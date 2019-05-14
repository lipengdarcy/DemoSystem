/**
 * 建库脚本
 **/

SET FOREIGN_KEY_CHECKS = 0; 

DROP TABLE IF EXISTS `sys_area_standard`;
CREATE TABLE `sys_area_standard` (
  `ID` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `pid` bigint(20) unsigned NOT NULL COMMENT '父编号',
  `postCode` char(6) DEFAULT NULL COMMENT '邮政编码',
  `short_name` varchar(255) DEFAULT NULL COMMENT '简称',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `mark_name` varchar(255) DEFAULT NULL COMMENT '省简称',
  `py` varchar(100) DEFAULT NULL COMMENT '简拼',
  `py_full` varchar(100) DEFAULT NULL COMMENT '全拼',
  `lng` varchar(20) DEFAULT NULL COMMENT '经度',
  `lat` varchar(20) DEFAULT NULL COMMENT '纬度',
  `level` tinyint(4) DEFAULT '0' COMMENT '级别(0-国家,1-省,2-市,3-区、县,4-乡、镇、街道,5-村、居委会)',
  `sort` int(11) DEFAULT NULL COMMENT '热度',
  `province_id` bigint(20) unsigned DEFAULT NULL COMMENT '省编号',
  `province_name` varchar(255) DEFAULT NULL COMMENT '省名称',
  `city_id` bigint(20) unsigned DEFAULT NULL COMMENT '市编号',
  `city_name` varchar(255) DEFAULT NULL COMMENT '市名称',
  `area_id` bigint(20) unsigned DEFAULT NULL COMMENT '区域ID',
  `area_name` varchar(255) DEFAULT NULL COMMENT '区域名称',
  `street_id` bigint(20) unsigned DEFAULT NULL COMMENT '乡镇街道编号',
  `street_name` varchar(500) DEFAULT NULL COMMENT '乡镇街道名称', 
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_valid` bit(1) DEFAULT b'1' COMMENT '是否有效（0-无效，1-有效）',
  PRIMARY KEY (`ID`),
  KEY `IDX_DISTRICT_ISVALID` (`is_valid`) USING BTREE,
  KEY `IDX_DISTRICT_LEVEL` (`province_id`,`city_id`,`area_id`) USING BTREE,
  KEY `IDX_DISTRICT_PARENT` (`pid`,`is_valid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行政区域表';

DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL COMMENT 'OSS存放路径',
  `file_name` varchar(100) DEFAULT NULL COMMENT '文件名称',
  `file_size` varchar(50) DEFAULT NULL COMMENT '文件大小',
  `description` varchar(1000) DEFAULT NULL COMMENT '描述',
  `md5` varchar(100) DEFAULT NULL COMMENT '文件md5值',
  `cid` int(11) DEFAULT '1' COMMENT '企业ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `update_by` int(11) DEFAULT NULL COMMENT '更新人',
  `is_valid` bit(1) DEFAULT b'1' COMMENT '是否有效',
  `mime_type` varchar(50) DEFAULT NULL COMMENT '文件类型',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='系统文件库';

DROP TABLE IF EXISTS `sys_industry`;
CREATE TABLE `sys_industry` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'id',
  `level` tinyint(4) DEFAULT '0' COMMENT '级别：1级、2级、3级',
  `pid` bigint(20) DEFAULT NULL COMMENT '父id',
  `name` varchar(255) DEFAULT NULL COMMENT '行业名称',
  `no_73` varchar(255) DEFAULT NULL COMMENT '73号令编号',
  `name_73` varchar(255) DEFAULT NULL COMMENT '73号令名称',
  `harm_level` varchar(255) DEFAULT NULL COMMENT '职业危害程度',
  `key_word` varchar(255) DEFAULT NULL COMMENT '关键字',
  `recommend_number` varchar(20) DEFAULT NULL COMMENT '分类编号',
  `describe_count` varchar(2000) DEFAULT NULL COMMENT '行业描述',
  `recommend` longtext COMMENT '推荐标准索引',
  `is_valid` bit(1) DEFAULT b'1' COMMENT '是否有效（0-无效，1-有效）',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '最后修改人ID',
  `update_date` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='行业分类';

DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) DEFAULT '1' COMMENT '企业ID',
  `user_id` bigint(11) DEFAULT NULL COMMENT '用户ID',
  `description` text COMMENT '日志内容简单说明',
  `user_name` varchar(100) DEFAULT NULL COMMENT '用户账号（冗余）',
  `user_fullname` varchar(100) DEFAULT NULL COMMENT '用户姓名（冗余）',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '用户代理信息',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `request_method` varchar(100) DEFAULT NULL COMMENT '操作方式：post/get/push等resetful',
  `module_name` varchar(100) DEFAULT NULL COMMENT '来源模块',
  `log_type` int(11) DEFAULT NULL COMMENT '类型(0-异常日志,1-登录日志,2-操作日志）',
  `log_params` text COMMENT '操作提交的数据内容(json)或者异常信息',
  `request_ip` varchar(50) DEFAULT NULL COMMENT '请求Ip',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` int(11) DEFAULT NULL COMMENT '更新人',
  `is_valid` bit(1) DEFAULT b'1' COMMENT '是否有效',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='记录用户登录、退出、操作系统资源、系统变化等信息';

DROP TABLE IF EXISTS `sys_message`;
CREATE TABLE `sys_message` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) DEFAULT NULL COMMENT '企业ID',
  `uid` int(11) DEFAULT NULL COMMENT '用户ID',
  `uname` varchar(50) DEFAULT NULL COMMENT '用户名字',
  `message` varchar(500) DEFAULT NULL COMMENT '消息主体',
  `group_id` int(11) DEFAULT NULL COMMENT '分组ID',
  `pid` int(11) DEFAULT NULL COMMENT '上一条消息ID',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态，1：未读；0：已读\n',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统消息';

DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `param_type` int(11) DEFAULT NULL COMMENT '参数类型id',
  `param_code` varchar(100) DEFAULT NULL COMMENT '参数编码',
  `param_value` varchar(100) DEFAULT NULL COMMENT '参数值',
  `description` varchar(500) DEFAULT NULL COMMENT '参数说明',
  `is_valid` tinyint(4) DEFAULT '1' COMMENT '是否可用',
  PRIMARY KEY (`ID`),
  KEY `FK_R_param_paramtype` (`param_type`),
  CONSTRAINT `FK_R_param_paramtype` FOREIGN KEY (`param_type`) REFERENCES `sys_param_type` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='系统参数';

DROP TABLE IF EXISTS `sys_param_type`;
CREATE TABLE `sys_param_type` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `type_value` varchar(100) DEFAULT NULL COMMENT '参数类型值',
  `type_code` varchar(100) DEFAULT NULL COMMENT '参数类型编码',
  `type_group` int(11) DEFAULT NULL COMMENT '类型分组',
  `pid` int(11) DEFAULT NULL COMMENT '参数类型上级id',
  `is_valid` tinyint(4) DEFAULT '1' COMMENT '是否可用',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='系统参数类型';

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `dept_id` int(11) DEFAULT NULL COMMENT '所在部门id',
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `role_type` int(11) DEFAULT '0' COMMENT '角色类型:0:待确定1.业务员2.其他。后续扩展',
  `role_code` varchar(100) DEFAULT NULL COMMENT '角色编码，示例：user,admin',
  `role_desc` varchar(1000) DEFAULT NULL COMMENT '角色描述',
  `is_sys` bit(1) DEFAULT NULL COMMENT '类型（是否系统）,true:系统角色,false:自定义角色',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `update_by` int(11) DEFAULT NULL COMMENT '更新人',
  `is_valid` bit(1) DEFAULT b'1' COMMENT '是否有效',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `permission_name` varchar(100) DEFAULT NULL COMMENT '权限名称',
  `permission_code` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `update_by` int(11) DEFAULT NULL COMMENT '更新人',
  `is_valid` bit(1) DEFAULT b'1' COMMENT '是否有效',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限';

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `staff_no` varchar(100) DEFAULT NULL COMMENT '工号',
  `cid` int(11) DEFAULT '1' COMMENT '三方企业ID',
  `type` tinyint(1) DEFAULT '1' COMMENT '类型:1.建安员工 2.手机端客户 默认1',
  `user_name` varchar(100) DEFAULT NULL COMMENT '用户名',
  `real_name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `pass_word` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(100) DEFAULT NULL COMMENT '盐值（系统生成）',
  `id_card` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门Id',
  `dept_join_date` datetime DEFAULT NULL COMMENT '进入部门时间',
  `tel` varchar(100) DEFAULT NULL COMMENT '联系电话（手机号码）',
  `duty_state` tinyint(4) DEFAULT '1' COMMENT '岗位状态:1-在岗、2-离岗',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` int(11) DEFAULT NULL COMMENT '更新人',
  `is_valid` bit(1) DEFAULT b'1' COMMENT '是否有效',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '用户ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色说明\n  0：管理员\n    1：普通用户\n',
  `role_name` varchar(500) DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(500) DEFAULT NULL COMMENT '角色代码（冗余）',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_valid` bit(1) DEFAULT b'1' COMMENT '是否有效',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=401 DEFAULT CHARSET=utf8mb4 COMMENT='用户角色';


