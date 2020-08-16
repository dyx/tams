#!/bin/bash

cd $(dirname $0)

docker-compose down
docker rmi tams_server
echo '删除服务完成'
rm -rf ~/tams
echo '删除数据完成'
echo '卸载完成'
