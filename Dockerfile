FROM openjdk:11
COPY build/libs/JavaSpringbootReactiveApiExemple-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]