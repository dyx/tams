#!/bin/bash

appName="tams"

docker-compose -p $appName down
docker rmi ${appName}-server
echo "应用卸载完成"

read -p "是否删除数据？(y/n): " response
response=$(echo "$response" | tr '[:upper:]' '[:lower:]')
if [ "$response" = "y" ]; then
  rm -rf /home/${appName}
  echo "数据已删除"
fi
