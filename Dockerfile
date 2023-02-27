FROM openjdk:17-oracle

WORKDIR /app
COPY ./build/libs/localstack-service-0.0.1-SNAPSHOT.jar /app

ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,address=*:22379,server=y,suspend=n","-jar","localstack-service-0.0.1-SNAPSHOT.jar"]
