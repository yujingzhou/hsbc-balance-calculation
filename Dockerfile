FROM registry.cn-beijing.aliyuncs.com/hub-mirrors/maven-jdk:v3.8.8-17.0.12 AS build
ADD ./start/target/hasbc-account.jar hasbc-account.jar
ENTRYPOINT ["java", "-jar", " hasbc-account.jar"]
