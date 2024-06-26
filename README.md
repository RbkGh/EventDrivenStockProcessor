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

## Overview 
```textmate
There are two main modules 
i.  product-service
- when a product is created, the server responds with 201 and adds the location of the resource in the "Location" header of the response
- if product exists, it returns conflicted httpstatus
ii. event-update-listener

- product-service starts a web service with exposed api's for product manipulation,saves to postgres.
- product-service also serves as the kafka producer that publishes any change in product to a kafka topic
- when an http request comes to create a product, immediately the product is successfully created, 
a product payload is published to "product.created" topic, when an update http request comes in, a payload is published to "product.updated" topic,
when a delete http request comes in, product deleted is published to "product.deleted"


- event-update-listener is a subscriber to the topics that are published by product service and it also stores the information in mongodb. 
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

![](https://github.com/RbkGh/EventDrivenStockProcessor/blob/main/photos/img_docker.png)

> [[/product-service-http-requests.postman_collection.json]](./product-service-http-requests.postman_collection.json) contains all CRUD http requests to use in PostMan

![](https://github.com/RbkGh/EventDrivenStockProcessor/blob/main/photos/test_cov.png)

> Swagger URL : [![http://localhost:8080/swagger-ui/index.html](https://img.shields.io/badge/swagger_url-000?style=for-the-badge&logo=ko-fi&logoColor=white)](http://localhost:8080/swagger-ui/index.html)
> Kafka-UI to view kafka updates as changes happen in real-time http://localhost:8090/

> CI/CD : Jenkinsfile is placed in product-service to deploy to heroku


## Run Tests- Navigate into /product-service and run:
```shell
./mvnw clean
```
```shell
./mvnw test
```
>>> this runs integration and unit tests with 100% class coverage using testcontainer for integration and mocks for unit service tests

![](https://github.com/RbkGh/EventDrivenStockProcessor/blob/main/photos/test_cov.png)