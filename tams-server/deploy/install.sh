#!/bin/bash

cd $(dirname $0)

docker-compose up -d

echo "**********正在初始化数据库，请耐心等待**********"
sleep 5
docker cp sql/init_ddl.sql tams_mysql:/home/init_ddl.sql
docker cp sql/init_dml.sql tams_mysql:/home/init_dml.sql
sleep 25
docker exec tams_mysql sh -c ' mysql -uroot -p123456 --protocol=tcp -e " use tams; source /home/init_ddl.sql; source /home/init_dml.sql;"'
echo '安装完成'
