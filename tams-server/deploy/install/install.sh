#!/bin/bash

appName="tams"
appBasePath=/home/${appName}

create_dir_and_copy() {
  local src="$1"
  local dest="$2"
  mkdir -p ${dest}
  cp ${src} ${dest}
}

docker build -f jdk.base.Dockerfile -t ${appName}-jdk .

create_dir_and_copy "*.sql" "${appBasePath}/mysql/init"
create_dir_and_copy "*.cnf" "${appBasePath}/mysql/conf"
create_dir_and_copy "*.conf" "${appBasePath}/nginx/conf.d"

docker-compose -p ${appName} up -d

docker stop "${appName}-server" "${appName}-web"

echo "**********正在启动后端服务，请耐心等待**********"
docker start "${appName}-server"
sleep 5
echo "**********后端服务启动成功**********"


if [ -d ./dist ]; then
  echo "**********正在启动前端服务，请耐心等待**********"
  rm -rf "${appBasePath}/nginx/html/*"
  cp -r ./dist/* "${appBasePath}/nginx/html/"
  docker start "${appName}-web"
  echo "**********前端服务启动成功 http://localhost:12011 **********"
fi

echo "**********安装完成**********"
