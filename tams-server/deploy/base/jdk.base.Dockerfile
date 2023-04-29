FROM eclipse-temurin:17-jre-alpine
ENV TZ=Asia/Shanghai
RUN set -xe && sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories \
    # ttf-dejavu fontconfig 图形验证码不显示问题
    && apk add --no-cache -U tzdata ttf-dejavu fontconfig \
    # 时差8小时问题
    && cp /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone \
    && apk del tzdata
