#!/bin/bash

cd $(dirname $0)

docker-compose down
docker rmi tams_server
docker-compose up -d
