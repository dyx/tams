#!/bin/bash

cd ../tams-web
yarn install
yarn build
cd ../tams-server
rm -rf src/main/resources/static
mv ../tams-web/dist src/main/resources/static
echo "**********前端构建完成**********"

mvn clean package -Pprod
echo "**********后端构建完成**********"

cd ..
rm -f tams.zip
mkdir tams-package
mkdir tams-package/target

cp -r tams-server/deploy/* tams-package
cp tams-server/Dockerfile tams-server/docker-compose.yaml tams-package
cp tams-server/target/*.jar tams-package/target

zip -r -m tams.zip tams-package

rm -rf tams-server/src/main/resources/static

cd tams-server
mvn clean compile
echo "**********打包完成**********"
