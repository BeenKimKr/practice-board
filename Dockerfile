FROM adoptopenjdk/openjdk11
LABEL maintainer="beenkim"
ARG JAR_FILE_PATH=./build/libs/*.jar
COPY ${JAR_FILE_PATH} springbootcicd/build-image.jar
CMD ["java","-jar","springbootcicdtest/build-image.jar"]