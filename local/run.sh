#!/bin/sh
docker run -d --hostname my-rabbit --name rabbit -p 5672:5672 -p 8080:15672   -e RABBITMQ_DEFAULT_USER=user -e RABBITMQ_DEFAULT_PASS=password rabbitmq:3-management