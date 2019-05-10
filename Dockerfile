FROM gradle:4.2.1-jdk8-alpine
WORKDIR /app
ADD . .

USER root
RUN gradle wrapper --gradle-version 4.2.1
RUN ./gradlew build

ENTRYPOINT ["java", "-jar", "build/libs/sahtel++.jar"]