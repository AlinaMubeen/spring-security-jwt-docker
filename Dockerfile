FROM eclipse-temurin:latest
ADD target/security.jar security.jar
ENTRYPOINT ["java","-jar","/security.jar"]