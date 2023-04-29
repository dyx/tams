#!/bin/bash

workdir=$(cd $(dirname $0); pwd)
appName="tams"
GREEN='\033[0;32m'
RED='\033[0;31m'
RESET='\033[0m'

function backend_package() {
  mvn -f ${workdir} clean package -Dmaven.test.skip=true -Pprod
  if [ $? -eq 0 ]; then
    cp ${workdir}/target/*.jar ${appName}_package
    mvn -f ${workdir} clean
    echo -e "********************${GREEN}后端打包成功${RESET}********************"
  else
    echo -e "********************${RED}后端打包失败${RESET}********************"
    exit 1
  fi
}

function frontend_package(){
  # 检查前端是否和后端再同一目录下
  if [ -d ${workdir}/../${appName}-web ] ;then
    npm --prefix ${workdir}/../${appName}-web install
    if [ $? -eq 0 ]; then
      npm --prefix ${workdir}/../${appName}-web run build
      if [ $? -eq 0 ]; then
        mv ${workdir}/../${appName}-web/dist/ ${workdir}/${appName}_package/dist
        echo -e "********************${GREEN}前端打包成功${RESET}********************"
      else
        echo -e "********************${RED}前端打包失败${RESET}********************"
        exit 1
      fi
    else
      echo -e "********************${RED}npm安装依赖失败，前端打包终止${RESET}********************"
      exit 1
    fi
  fi
}

function prepare_package() {
  rm -rf ${appName}.zip ${appName}_package
  mkdir -p ./${appName}_package
  cp ./deploy/base/* ./deploy/install/* ${appName}_package
}

prepare_package
backend_package
frontend_package

zip -r -m ${appName}.zip ${appName}_package
