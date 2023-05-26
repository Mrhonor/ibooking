# 设置基础镜像，可以根据项目需求选择不同的基础镜像
FROM openjdk:17-jdk

# 设置工作目录
WORKDIR /app

# 复制编译后的JAR文件到工作目录
COPY target/ibooking-0.01.jar /app

# 暴露应用程序的端口，根据你的Spring Boot应用的配置进行修改
EXPOSE 8090

# 运行应用程序
CMD ["java", "-jar", "ibooking-0.01.jar"]
