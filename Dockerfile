FROM openjdk:11-jdk
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} target/test-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/test-0.0.1-SNAPSHOT.jar"]