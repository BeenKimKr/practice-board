FROM adoptopenjdk/openjdk11
LABEL maintainer="beenkim"
ARG JAR_FILE_PATH=./build/libs/*.jar
ADD ${JAR_FILE_PATH} beenkim/springbootcicd.jar
CMD ["java","-jar","beenkim/springbootcicd.jar"]