FROM eclipse-temurin:17-jdk-alpine
ADD build/libs/library-0.1.jar library-0.1.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/library-0.1.jar"]