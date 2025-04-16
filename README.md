# HSBC-ACCOUNT

本工程使用 jdk17 + spring 实现, 并采用阿里云 ack 作为服务管理

## 工程架构
工程采用阿里巴巴开源框架 Cola 为基础搭建，整体模块构成如下：

![cola.png](document/images/%E6%9E%B6%E6%9E%84%E8%AE%BE%E8%AE%A1/cola.png)

## 系统详细设计

核心功能为账户之间转账，domain 包含 Transaction、Account，并通过 TransactionEvent 作为本地消息表来存储转账行为，同时采用 Redis 缓存和 mns 消息队列来提高处理转账的能力。

系统设计如下：
![应用架构.png](document/images/%E6%9E%B6%E6%9E%84%E8%AE%BE%E8%AE%A1/%E5%BA%94%E7%94%A8%E6%9E%B6%E6%9E%84.png)

## 部署
工程部署在阿里云 ACK 中，采用阿里云 RDS、Redis、mns 作为中间件，自建 k8s 集群，并采用阿里云最新 nlb 作为 接入、负载及服务发现

项目架构及部署如下：

![项目架构.png](document/images/%E6%9E%B6%E6%9E%84%E8%AE%BE%E8%AE%A1/%E9%A1%B9%E7%9B%AE%E6%9E%B6%E6%9E%84.png)

k8s 集群如下：
![ack集群.png](document/images/%E6%9E%B6%E6%9E%84%E8%AE%BE%E8%AE%A1/ack%E9%9B%86%E7%BE%A4.png)

nlb 如下：
![nlb.png](document/images/%E6%9E%B6%E6%9E%84%E8%AE%BE%E8%AE%A1/nlb.png)

部署步骤：

- #### 1. 工程打包 maven clean install -DskipTests
- #### 2. 执行Docker镜像打包 docker build -t hsbc-account:v1.0.1 .
- #### 3. 提交镜像到阿里云Repository docker push crpi-c1fosxni6gavqetm.cn-beijing.personal.cr.aliyuncs.com/hsbc-interview/hsbc-account:v1.0.1
- #### 4. 在阿里云ack 执行滚动更新服务

## 测试
- #### 1. 单元测试: 参见 document/images/unit-test
- #### 2. hpa测试：参见 document/images/hpa-test






