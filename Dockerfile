FROM adoptopenjdk/openjdk11
LABEL maintainer="beenkim"
CMD ["./mvnw", "clean", "package"]
ARG JAR_FILE_PATH=./build/libs/*.jar
ADD ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]