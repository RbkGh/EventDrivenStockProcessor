# Stock Processor Service

[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)

## Requirements
```textmate
1. Java 17
"java --version" on the command line must return a version 17 java like:
openjdk 17.0.9 2023-10-17
OpenJDK Runtime Environment Homebrew (build 17.0.9+0)
OpenJDK 64-Bit Server VM Homebrew (build 17.0.9+0, mixed mode, sharing)


```

## Run Tests(Follow Sequence)
```shell
./mvnw clean
```
```shell
./mvnw test
```

## Run service(Follow Sequence)
```shell
docker-compose build
```
```shell
docker-compose up
```