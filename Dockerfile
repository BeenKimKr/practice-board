FROM adoptopenjdk/openjdk11

LABEL maintainer="beenkim"

ARG JAR_FILE_PATH=./build/libs/*.jar
ADD ${JAR_FILE_PATH} practice-board-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "app.jar"]