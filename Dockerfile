FROM adoptopenjdk/openjdk11
VOLUME /tmp
LABEL maintainer="beenkim"
ARG JAR_FILE_PATH=./build/libs/*SNAPSHOT.jar
COPY ${JAR_FILE_PATH} app.jar
CMD ["java","-jar","app.jar"]