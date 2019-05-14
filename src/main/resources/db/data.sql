/**
 * 数据初始化脚本
 **/

SET FOREIGN_KEY_CHECKS = 0; 




-- 用户表，用户名：admin  密码： 111111
INSERT INTO `sys_user` (`ID`,`user_name`,`real_name`,`pass_word`,`salt`) VALUES 
(1,'admin','管理员','453144c00bf7dd0a289898bbdf5f6cd0b3a94aa4','d9135f28558fb219');
