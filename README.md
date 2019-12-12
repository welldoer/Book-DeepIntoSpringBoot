# Book-DeepIntoSpringBoot

阅读《深入实践 Spring Boot》，学习 SpringBoot 开发技能
  Deep into Spring Boot，陈韶健 著，机械工业出版社，2016.10

[![Build Status](https://travis-ci.com/welldoer/Book-DeepIntoSpringBoot.svg?branch=master)](https://travis-ci.com/welldoer/Book-DeepIntoSpringBoot)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=welldoer_Book-DeepIntoSpringBoot&metric=coverage)](https://sonarcloud.io/dashboard?id=welldoer_Book-DeepIntoSpringBoot)


## 初始环境准备清单

~~1. 在 GitHub 中新建 repo~~

~~2. 在本地 Eclipse 开发环境中 Clone repo，建立相应的开发目录~~

~~3. 修改脚本 curl.sh 并执行，下载定制的 SpringBoot 开发包~~

~~4. 修改 build.gradle，添加国内的 plugin、jar 访问地址~~

~~5. 执行 ./gradlew check，确认相关依赖、测试就绪~~

~~6. 在 Eclipse 中 import project，添加 gradle 上下文，确保工程配置无报错信息~~

~~7. 基于自动导入的代码，验证环境可用；确认 .gitignore，提交代码~~

~~8. 配置基本的 Travis-CI 环境，添加对应 Badge~~

~~9. 配置 SonarCloud 环境，向 Travis 中添加 SONAR_TOKEN 变量，添加常用的依赖~~

~~10. 补充 Badges 信息。至此，环境准备工作结束~~


## 章节学习清单

### 第一部分 基础应用开发

#### 第 1 章  Spring Boot 入门

- [X] 1.1 配置开发环境
- [X] 1.2 创建项目工程
- [X] 1.3 使用 Spring Boot
- [X] 1.4 运行与发布

#### 第 2 章  在 Spring Boot 中使用数据库

- [X] 2.1 使用 MySQL
- [X] 2.2 使用 Redis
- [X] 2.3 使用 MongoDB
- [X] 2.4 使用 Neo4j
- [X] 2.5 小结

#### 第 3 章  Spring Boot 界面设计

- [X] 3.1 模型设计
- [X] 3.2 控制器设计
- [X] 3.3 使用 Thymeleaf 模板
- [ ] 3.4 视图设计
- [ ] 3.5 运行与发布
- [ ] 3.6 小结

#### 第 4 章  提高数据库访问性能

- [ ] 4.1 使用 Druid
- [ ] 4.2 扩展 JPA 功能
- [ ] 4.3 使用 Redis 做缓存
- [ ] 4.4 Web 应用模块
- [ ] 4.5 运行与发布
- [ ] 4.6 小结

#### 第 5 章  Spring Boot 安全设计

- [ ] 5.1 依赖配置管理
- [ ] 5.2 安全策略配置
- [ ] 5.3 登录认证设计
- [ ] 5.4 权限管理设计
- [ ] 5.5 根据权限设置链接
- [ ] 5.6 运行与发布
- [ ] 5.7 小结

## 开发备注

> 针对 WebUsingNeo4j 工程，初次运行时执行如下操作：
>
> 1. 下载镜像：`docker pull neo4j:3.5.0`
> 2. 运行容器：`docker run -d -p 7474:7474 -p 7687:7687 --name neo4j-3.5.0 neo4j:3.5.0`
> 3. 浏览器 http://localhost:7474/ 访问 neo4j 管理后台，初始账号/密码 neo4j/neo4j，会要求修改初始化密码，我们修改为 neo4j/admin
> 4. 执行 SpringBoot 应用
>
> 重启容器指令：
>
> 1. 停止容器：`docker stop neo4j-3.5.0`
> 2. 启动容器：`docker start neo4j-3.5.0`
>


