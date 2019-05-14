# 项目说明

1. 项目基于springboot-2.1.3开发

2. 持久层：mybatis

3. 展现层：freemarker


## 代码目录说明

1. 包名以cn.smarthse开头

2. cn.smarthse.business: 业务代码

3. cn.smarthse.config: 配置文件

4. cn.smarthse.framework: 框架相关，如拦截器、过滤器、工具类等



## 打包发布说明

1. 测试库打包
mvn clean package -Pdev

2. 正式库打包
mvn clean package -Pproduct
