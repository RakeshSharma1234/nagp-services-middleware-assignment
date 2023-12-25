## Prerequisite
1. Maven build tool latest
2. OpenJdk 17.0.8
3. RabbitMQ Management server either on docker(rabbitmq:3.9.29-management-alpine) or installer should be running on tcp port 
5672 with username and password as `guest` (For more details check the `src/main/resources/application.properties`).
4. RabbitMQ Exchanges and Queues will be created as mentioned in the Application Run Section.

## gRPC proto files in path
1. `src/main/proto/*`
2. `src/main/proto-import/*`

## Application Server Configuration
For each microservice under path `src/main/resources/application.properties`

## RabbitMQ queues and exchanges

### RabbitMQ Queues
1. com.nagp.place-order.fanout.queue1
2. com.nagp.place-order.fanout.queue2
3. com.nagp.update-order.topic.queue (Routing Key for this queue is *)

### RabbitMQ Exchanges
1. com.nagp.place-order.fanout.exchange
2. com.nagp.update-order.topic.exchange

## Application Compile, Build and Run

### 1. Compile and Build
Run `mvn clean install` in each service will compile the code and generate the
grpc proto buffers stub classes into ``src/main/generated/`` and build the
final application jar file into `target/` folder

### 2. Run the application
**Note:**
1. For first time Run the Order service in order to create the exchanges and queues automatically on rabbitMQ.
2. RabbitMQ exchanges and queues will be created on Order Service Startup.
3. After this you can start rest of the services.

Run the below commands in each service base directory to start each application:
1. `java -jar target/order-service-0.0.1-SNAPSHOT.jar`
2. `java -jar target/product-service-0.0.1-SNAPSHOT.jar`
3. `java -jar target/notification-service-1-0.0.1-SNAPSHOT.jar`
4. `java -jar target/notification-service-2-0.0.1-SNAPSHOT.jar`


## Product Service cURL Requests

### Get Product List
curl --location 'http://localhost:8081/product/list'

### Place Order
curl --location 'http://localhost:8081/product/placeOrder' \
--header 'Content-Type: application/json' \
--data '{
"productId":8787,
"quantity":56,
"price":3232
}'
### Update Order
curl --location --request PUT 'http://localhost:8081/product/updateOrder/5466'