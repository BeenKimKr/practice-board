FROM adoptopenjdk/openjdk11
LABEL maintainer="beenkim"
ARG JAR_FILE_PATH=repo/build/libs/*.jar
COPY ${JAR_FILE_PATH} app.jar
CMD ["java","-jar","app.jar"]