FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
ENV TZ=Asia/Shanghai
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories \
    && apk add -U tzdata \
    && cp /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ > /etc/timezone
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
