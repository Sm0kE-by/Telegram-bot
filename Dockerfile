FROM gradle:8.10.2-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM eclipse-temurin:23.0.1_11-jre-ubi9-minimal
EXPOSE 8080
RUN mkdir /app
#ENV TOKEN=XXX
COPY --from=build /home/gradle/src/build/libs/ /app/
ENTRYPOINT ["java","-jar","/app/demo_bot-0.0.1-SNAPSHOT.jar"]