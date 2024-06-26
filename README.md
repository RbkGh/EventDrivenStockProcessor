# Stock Processor Service

[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)

## Requirements
```textmate
1. Java 17+
"java --version" on the command line must return a version 17 java like:
openjdk 17.0.9 2023-10-17
OpenJDK Runtime Environment Homebrew (build 17.0.9+0)
OpenJDK 64-Bit Server VM Homebrew (build 17.0.9+0, mixed mode, sharing)


```

## Run all services: 
> First off,rename ".example.env" file to ".env" [this loads the values into docker]

2.
```shell
docker-compose build
````
3.
```shell
docker-compose up
````
>>> this above starts kafka and zookeper, starts two services(product-service and event-update-listener) and starts two databases, mongo_db and postgres_db

> # [[/product-service-http-requests.postman_collection.json]](./product-service-http-requests.postman_collection.json) contains all CRUD http requests to use in PostMan
> # Swagger URL : [![http://localhost:8080/swagger-ui/index.html](https://img.shields.io/badge/swagger_url-000?style=for-the-badge&logo=ko-fi&logoColor=white)](http://localhost:8080/swagger-ui/index.html)
> # Kafka-UI to view kafka updates as changes happen in real-time http://localhost:8090/


## Run Tests- Navigate into /product-service and run:
```shell
./mvnw clean
```
```shell
./mvnw test
```
>>> this runs integration and unit tests with 100% class coverage using testcontainer for integration and mocks for unit service tests
![](https://github.com/RbkGh/EventDrivenStockProcessor/blob/main/photos/img_docker.png)