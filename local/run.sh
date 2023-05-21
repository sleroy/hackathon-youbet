#!/bin/sh
docker run -d --hostname my-rabbit --name rabbit -p 5672:5672 -p 8080:15672   -e RABBITMQ_DEFAULT_USER=user -e RABBITMQ_DEFAULT_PASS=password rabbitmq:3-management

docker run --detach --name some-mariadb --env MARIADB_USER=user --env MARIADB_PASSWORD=password --env MARIADB_ROOT_PASSWORD=password -p 3306:3306 mariadb:latest