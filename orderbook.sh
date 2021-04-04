#!/bin/bash
echo "Run CBP Orderbook for $1"
./mvnw spring-boot:run -Dspring-boot.run.arguments=--subscribe.productId=$1
