#!/bin/bash

appName="tams"

if [ -f *.jar ]; then
  echo "**********正在升级后端服务，请耐心等待**********"
  docker-compose -p ${appName} build backend
  # 删除dangling image
  docker rmi $(docker images -q -f dangling=true)
  docker-compose -p ${appName} up -d --no-deps backend
  echo "**********后端服务升级完成**********"
fi

if [ -d ./dist ]; then
  echo "**********正在升级前端服务，请耐心等待**********"
  rm -rf /home/${appName}/nginx/html/*
  cp -r ./dist/* /home/${appName}/nginx/html/
  echo "**********前端服务升级完成 http://localhost:12011 **********"
fi
