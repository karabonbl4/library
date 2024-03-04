FROM openjdk:17
CMD ["./gradlew", "clean", "build"]
COPY build/libs/library-0.1.jar library.jar
ENTRYPOINT ["java","-jar","/library.jar"]