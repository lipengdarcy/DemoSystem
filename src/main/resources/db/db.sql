/**
 * 建库脚本
 **/

SET FOREIGN_KEY_CHECKS = 0; 


DROP TABLE IF EXISTS `best_contract`;
CREATE TABLE `best_contract` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sub_company` varchar(200) DEFAULT NULL COMMENT '分公司',
  `project_name` varchar(200) DEFAULT NULL COMMENT '项目名称',
  `begin_time` date DEFAULT NULL COMMENT '开始时间',
  `end_time` date DEFAULT NULL COMMENT '结束时间',
  `manager` varchar(200) DEFAULT NULL COMMENT '销售经理',
  `contract_no` varchar(200) DEFAULT NULL COMMENT '合同编码',
  `contract_type` varchar(200) DEFAULT NULL COMMENT '合同类型',
  `seal_date` varchar(200) DEFAULT NULL COMMENT '盖章时间',
  `is_back` varchar(200) DEFAULT NULL COMMENT '是否返回',
  `agent_policy` varchar(200) DEFAULT NULL COMMENT '代理政策',
  `member_fee` varchar(200) DEFAULT NULL COMMENT '会员费',
  `implement_fee` varchar(200) DEFAULT NULL COMMENT '实施费',
  `flow_fee` varchar(200) DEFAULT NULL COMMENT '系统联调费/流量费',
  `actual_fee` varchar(200) DEFAULT NULL COMMENT '实际打款',
  `member_time` date DEFAULT NULL COMMENT '会员费回款时间',
  `implement_time` date DEFAULT NULL COMMENT '实施费回款时间',
  `flow_time` date DEFAULT NULL COMMENT '联调费回款时间',
  `contract_achive_no` varchar(200) DEFAULT NULL COMMENT '合同存档编号',
  `mail_address` varchar(200) DEFAULT NULL COMMENT '合同邮寄地址',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8 COMMENT='代理协议';


DROP TABLE IF EXISTS `best_project`;
CREATE TABLE `best_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sub_company` varchar(200) DEFAULT NULL COMMENT '分公司',
  `project_name` varchar(200) DEFAULT NULL COMMENT '项目名称',
  `begin_time` date DEFAULT NULL COMMENT '开始时间',
  `end_time` date DEFAULT NULL COMMENT '结束时间',
  `manager` varchar(200) DEFAULT NULL COMMENT '销售经理',
  `contract_no` varchar(200) DEFAULT NULL COMMENT '合同编码',
  `contract_type` varchar(200) DEFAULT NULL COMMENT '合同类型',
  `server_company` varchar(200) DEFAULT NULL COMMENT '服务商',
  `actual_user` varchar(200) DEFAULT NULL COMMENT '实际使用客户',
  `seal_date` varchar(200) DEFAULT NULL COMMENT '盖章时间',
  `start_receive` varchar(200) DEFAULT NULL COMMENT '开始入账月份',
  `contract_back` varchar(200) DEFAULT NULL COMMENT '合同返回日期',
  `product` varchar(200) DEFAULT NULL COMMENT '产品',
  `member_fee` varchar(200) DEFAULT NULL COMMENT '会员费',
  `implement_fee` varchar(200) DEFAULT NULL COMMENT '实施费',
  `flow_fee` varchar(200) DEFAULT NULL COMMENT '系统联调费/流量费',
  `actual_fee` varchar(200) DEFAULT NULL COMMENT '实际打款',
  `member_time` date DEFAULT NULL COMMENT '会员费回款时间',
  `implement_time` date DEFAULT NULL COMMENT '实施费回款时间',
  `flow_time` date DEFAULT NULL COMMENT '联调费回款时间',
  `member_finish_percent` varchar(200) DEFAULT NULL COMMENT '会员完成情况',
  `contract_achive_no` varchar(200) DEFAULT NULL COMMENT '合同存档编号',
  `billing` varchar(200) DEFAULT NULL COMMENT '开票',
  `mail_address` varchar(200) DEFAULT NULL COMMENT '合同邮寄地址',
  `a1` varchar(200) DEFAULT NULL COMMENT '类型',
  `a2` varchar(200) DEFAULT NULL COMMENT '内部返佣比例',
  `a3` varchar(200) DEFAULT NULL COMMENT '外部返佣比例',
  `a4` varchar(200) DEFAULT NULL COMMENT '已付外部返佣金额',
  `a5` varchar(200) DEFAULT NULL COMMENT '客户属性',
  `a6` varchar(200) DEFAULT NULL COMMENT '续签率',
  `a7` varchar(200) DEFAULT NULL COMMENT '业务运行数据',
  `a8` varchar(200) DEFAULT NULL COMMENT '实施开始日期',
  `a9` varchar(200) DEFAULT NULL COMMENT '实施结束日期',
  `a10` varchar(200) DEFAULT NULL COMMENT 'BDC开通',
  `a11` varchar(200) DEFAULT NULL COMMENT 'TMS开通',
  `a12` varchar(200) DEFAULT NULL COMMENT 'WMS开通',
  `a13` varchar(200) DEFAULT NULL COMMENT '项目是否关停',
  `a14` varchar(200) DEFAULT NULL COMMENT '项目关停时间',
  `a15` varchar(200) DEFAULT NULL COMMENT '简要原因',
  `a16` varchar(200) DEFAULT NULL COMMENT '实施经理',
  `a17` varchar(200) DEFAULT NULL COMMENT '经销商',
  `a18` varchar(200) DEFAULT NULL COMMENT '物流商',
  `a19` varchar(200) DEFAULT NULL COMMENT '店加',
  `a20` varchar(200) DEFAULT NULL COMMENT '服装商',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=347 DEFAULT CHARSET=utf8 COMMENT='产品信息表，包含客户、合同、项目';


DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `name` varchar(100) DEFAULT NULL COMMENT '企业名称',
  `short_name` varchar(100) DEFAULT NULL COMMENT '企业名称简称',
  `father_dept` varchar(100) DEFAULT NULL COMMENT '上属公司（或主管单位）',
  `setting_time` date DEFAULT NULL COMMENT '成立时间',
  `unified_social_cc` varchar(100) DEFAULT NULL COMMENT '统一社会信用代码（此处该代码用于显示，由平台申请企业认证赋予数据）',
  `company_contact_tel` varchar(20) DEFAULT NULL COMMENT '企业联系电话	',
  `company_contact_email` varchar(50) DEFAULT NULL COMMENT '企业电子邮箱',
  `company_contact_fax` varchar(20) DEFAULT NULL COMMENT '企业传真',
  `area_id` bigint(20) unsigned DEFAULT NULL COMMENT '行政区域区编号(SYS_DISTRICT2.id)',
  `register_address` varchar(100) DEFAULT NULL COMMENT '企业注册地详址',
  `register_post` varchar(6) DEFAULT NULL COMMENT '企业注册地址邮编',
  `company_postal_adress` varchar(500) DEFAULT NULL COMMENT '企业通讯地址',
  `work_address` varchar(100) DEFAULT NULL COMMENT '工作产所地址（工作场所地址）',
  `register_type` int(11) DEFAULT NULL COMMENT '注册类型',
  `industry_category` bigint(20) DEFAULT NULL COMMENT '行业分类（sys_param.param_code）',
  `occupational_risk_classify` tinyint(4) DEFAULT NULL COMMENT '职业病危害风险分类(0-严重、1-较重、2-一般)',
  `staff_count` int(5) DEFAULT NULL COMMENT '在岗职工人数',
  `pickup_count` int(5) DEFAULT '0' COMMENT '接害人数（不重复计）',
  `main_opera_income` decimal(10,2) DEFAULT NULL COMMENT '主营营业收入（万元）',
  `register_capital` decimal(10,2) DEFAULT NULL COMMENT '注册资本',
  `scale` varchar(100) DEFAULT NULL COMMENT '企业规模',
  `year_assets` decimal(10,2) DEFAULT NULL COMMENT '企业年度产值',
  `total_assets` decimal(10,2) DEFAULT NULL COMMENT '企业资产总值',
  `legal_name` varchar(100) DEFAULT NULL COMMENT '法人代表姓名',
  `legal_tel` varchar(100) DEFAULT NULL COMMENT '法人代表联系电话',
  `chemical_emergency_phone` varchar(45) DEFAULT NULL COMMENT '化学事故应急咨询服务电话',
  `occ_leader_sid` bigint(20) unsigned DEFAULT NULL COMMENT '职业卫生负责人',
  `occ_leader_tel` varchar(20) DEFAULT NULL COMMENT '职业卫生负责人联系电话',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) unsigned DEFAULT NULL COMMENT '创建人',
  `update_date` datetime DEFAULT NULL COMMENT '最后修改时间',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '最后修改人ID',
  `is_valid` bit(1) DEFAULT b'1' COMMENT '是否有效（0-无效，1-有效）',
  `logo_file_url` varchar(500) DEFAULT NULL COMMENT '企业logo url 地址',
  PRIMARY KEY (`id`),
  KEY `area_id_index` (`area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业信息';


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
  `is_valid` bit(1) DEFAULT b'1' COMMENT '是否有效（0-无效，1-有效）',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  KEY `IDX_DISTRICT_PARENT` (`pid`) USING BTREE,
  KEY `IDX_DISTRICT_LEVEL` (`level`,`pid`) USING BTREE,
  KEY `IDX_DISTRICT_ISVALID` (`is_valid`) USING BTREE,
  KEY `index5` (`is_valid`,`level`) USING BTREE
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统文件库';


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='记录用户登录、退出、操作系统资源、系统变化等信息';


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统参数';


DROP TABLE IF EXISTS `sys_param_type`;
CREATE TABLE `sys_param_type` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `type_value` varchar(100) DEFAULT NULL COMMENT '参数类型值',
  `type_code` varchar(100) DEFAULT NULL COMMENT '参数类型编码',
  `type_group` int(11) DEFAULT NULL COMMENT '类型分组',
  `pid` int(11) DEFAULT NULL COMMENT '参数类型上级id',
  `is_valid` tinyint(4) DEFAULT '1' COMMENT '是否可用',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统参数类型';


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';


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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';


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

DROP TABLE IF EXISTS `temp_company`;
CREATE TABLE `temp_company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(200) DEFAULT NULL COMMENT '编码',
  `name` varchar(200) DEFAULT NULL COMMENT '企业名称',
  `manager` varchar(200) DEFAULT NULL COMMENT '负责人',
  `tel` varchar(200) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(200) DEFAULT NULL COMMENT '注册地址',
  `update_date` varchar(200) DEFAULT NULL COMMENT '备案日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64414 DEFAULT CHARSET=utf8 COMMENT='浙江省备案企业';


DROP TABLE IF EXISTS `temp_company_500`;
CREATE TABLE `temp_company_500` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(200) DEFAULT NULL COMMENT '编码',
  `name` varchar(200) DEFAULT NULL COMMENT '企业名称',
  `manager` varchar(200) DEFAULT NULL COMMENT '负责人',
  `tel` varchar(200) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(200) DEFAULT NULL COMMENT '注册地址',
  `update_date` varchar(200) DEFAULT NULL COMMENT '备案日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64366 DEFAULT CHARSET=utf8 COMMENT='浙江省备案企业，没有找到区域的';


DROP TABLE IF EXISTS `tnet_order`;
CREATE TABLE `tnet_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `domain_name` varchar(200) DEFAULT NULL COMMENT '域名称',
  `company_name` varchar(200) DEFAULT NULL COMMENT '公司名称',
  `create_time` datetime DEFAULT NULL COMMENT '域创建时间',
  `commit_count` int(11) DEFAULT '0' COMMENT '已提交订单量',
  `finish_count` int(11) DEFAULT '0' COMMENT '已完成订单量',
  `jf_count` int(11) DEFAULT '0' COMMENT '已计费订单量',
  `cz_count` int(11) DEFAULT '0' COMMENT '已出账订单量',
  `mobile_count` int(11) DEFAULT '0' COMMENT '移动端活动上报订单量',
  `smart_count` int(11) DEFAULT '0' COMMENT '智能调度订单量',
  `weixin_count` int(11) DEFAULT '0' COMMENT '微信下单量',
  `up_partner_count` int(11) DEFAULT '0' COMMENT '有效上游Tnet合作伙伴数量',
  `down_partner_count` int(11) DEFAULT '0' COMMENT '有效下游Tnet合作伙伴数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8 COMMENT='tnet周单量统计报表';


DROP TABLE IF EXISTS `wx_department`;
CREATE TABLE `wx_department` (
  `local_id` int(11) NOT NULL AUTO_INCREMENT,
  `id` int(11) NOT NULL COMMENT '微信部门id',
  `name` varchar(200) DEFAULT NULL COMMENT '部门名称',
  `parentid` int(11) DEFAULT NULL COMMENT '上级id',
  `order_by` int(11) DEFAULT NULL COMMENT '排序id',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `path` varchar(200) DEFAULT NULL COMMENT '组织架构全路径',
  PRIMARY KEY (`local_id`),
  KEY `index_path` (`path`) USING BTREE,
  KEY `index_id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=67608 DEFAULT CHARSET=utf8 COMMENT='微信部门表';


DROP TABLE IF EXISTS `wx_department_temp`;
CREATE TABLE `wx_department_temp` (
  `local_id` int(11) NOT NULL AUTO_INCREMENT,
  `id` int(11) NOT NULL COMMENT '微信部门id',
  `name` varchar(200) DEFAULT NULL COMMENT '部门名称',
  `parentid` int(11) DEFAULT NULL COMMENT '上级id',
  `order_by` int(11) DEFAULT NULL COMMENT '排序id',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `path` varchar(500) DEFAULT NULL COMMENT '组织架构全路径',
  PRIMARY KEY (`local_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3243 DEFAULT CHARSET=utf8 COMMENT='微信部门表';


DROP TABLE IF EXISTS `wx_user`;
CREATE TABLE `wx_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) DEFAULT NULL COMMENT '企业id',
  `userid` varchar(200) NOT NULL COMMENT '微信用户id',
  `name` varchar(200) DEFAULT NULL COMMENT '姓名',
  `department` varchar(200) DEFAULT NULL COMMENT '部门id，可以多个',
  `position` varchar(200) DEFAULT NULL COMMENT '职位',
  `mobile` varchar(200) DEFAULT NULL COMMENT '手机',
  `gender` tinyint(4) DEFAULT '0' COMMENT '性别。0表示未定义，1表示男性，2表示女性',
  `email` varchar(200) DEFAULT NULL COMMENT 'email',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `status` tinyint(4) DEFAULT '1' COMMENT '激活状态: 1=已激活，2=已禁用，4=未激活 已激活代表已激活企业微信或已关注微工作台（原企业号）。未激活代表既未激活企业微信又未关注微工作台（原企业号）',
  `enable` tinyint(4) DEFAULT '1' COMMENT '成员启用状态。1表示启用的成员，0表示被禁用。服务商调用接口不会返回此字段',
  `isleader` tinyint(4) DEFAULT '0' COMMENT '是否领导',
  `extattr` varchar(200) DEFAULT NULL COMMENT '其他信息，json字符串 ，key为attrs',
  `hide_mobile` varchar(200) DEFAULT NULL COMMENT '隐藏手机',
  `english_name` varchar(200) DEFAULT NULL COMMENT '英文名',
  `telephone` varchar(200) DEFAULT NULL COMMENT '座机',
  `order_by` varchar(200) DEFAULT NULL COMMENT '部门内的排序值，32位整数，默认为0。数量必须和department一致',
  `qr_code` varchar(200) DEFAULT NULL COMMENT '二维码',
  `alias` varchar(200) DEFAULT NULL COMMENT '别名；第三方仅通讯录应用可获取',
  `is_leader_in_dept` varchar(200) DEFAULT NULL COMMENT '表示在所在的部门内是否为上级；第三方仅通讯录应用可获取',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门id',
  PRIMARY KEY (`id`),
  KEY `index_dept` (`dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='微信人员表';


DROP TABLE IF EXISTS `you_order`;
CREATE TABLE `you_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `domain_name` varchar(200) DEFAULT NULL COMMENT '域名',
  `domain_code` varchar(200) DEFAULT NULL COMMENT '域编码',
  `create_time` date DEFAULT NULL COMMENT '建域时间',
  `use_day_count` int(11) DEFAULT '0' COMMENT '使用时间(天)',
  `active_count` int(11) DEFAULT '0' COMMENT '总活跃量',
  `sale_count_tolal` int(11) DEFAULT '0' COMMENT '销售单量(总)',
  `sale_count_program` int(11) DEFAULT '0' COMMENT '销售单量(小程序)',
  `sale_value` float DEFAULT '0' COMMENT '销售单总金额(元)',
  `sale_stockout_count_total` int(11) DEFAULT '0' COMMENT '销售出库单量(总)',
  `sale_stockout_count_program` int(11) DEFAULT '0' COMMENT '销售出库单量(小程序)',
  `sale_stockout_value` float DEFAULT '0' COMMENT '销售出库单总金额(元)',
  `purchase_count` int(11) DEFAULT '0' COMMENT '采购单量',
  `purchase_value` float DEFAULT '0' COMMENT '采购单总金额(元)',
  `purchase_stockin_count` int(11) DEFAULT '0' COMMENT '采购入库单量',
  `purchase_stockin_value` float DEFAULT '0' COMMENT '采购入库单总金额(元)',
  `stockin_count` int(11) DEFAULT '0' COMMENT '入库单量',
  `stockout_count` int(11) DEFAULT '0' COMMENT '出库单量',
  `active_job_count` int(11) DEFAULT '0' COMMENT '进行中任务量',
  `stock_report_count` int(11) DEFAULT '0' COMMENT '库存上报记录量',
  `other_pay_value` float DEFAULT '0' COMMENT '其它付款数量',
  `main_pay_value` float DEFAULT '0' COMMENT '主营付款数量',
  `main_receive_value` float DEFAULT '0' COMMENT '主营收款数量',
  `other_receive_value` float DEFAULT '0' COMMENT '其它收款数量',
  `inner_transfer_value` float DEFAULT '0' COMMENT '内部转账数量',
  `adjust_value` float DEFAULT '0' COMMENT '应收应付调整数量',
  `order_count` int(11) DEFAULT '0' COMMENT '运单数量',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8 COMMENT='优赢日单量表（每日更新）';







