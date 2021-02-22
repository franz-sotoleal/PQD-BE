FROM openjdk:11
COPY configuration/build/libs/configuration.jar pqd-be.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "pqd-be.jar"]
