cd ./Eureka-service
call mvn clean install -DskipTests
cd ../Config-server
call mvn clean install -DskipTests
cd ../api-gateway
call mvn clean install -DskipTests
cd ../User-Microservice
call mvn clean install -DskipTests
cd ../ClientCare-Microservice
call mvn clean install -DskipTests
cd ../Vehicle-Microservice
call mvn clean install -DskipTests
cd ../Notification-Microservice
call mvn clean install -DskipTests
cd ../System-events
call mvn clean install -DskipTests
cd ../frontend
call npm install
call ng build --configuration production
cd ../
call docker-compose up --build
pause 9999999999999999
