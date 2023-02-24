FROM openjdk:17-oracle

WORKDIR /app
COPY ./build/libs/localstack-service-0.0.1-SNAPSHOT.jar /app
EXPOSE 22378

CMD ["java", "-jar", "localstack-service-0.0.1-SNAPSHOT.jar"]
