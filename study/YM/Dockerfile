FROM eclipse-temurin:11-jdk-alpine
VOLUME /tmp
ENV PORT 8080
EXPOSE 8080
COPY ./build/libs/*.jar /app/
ENTRYPOINT ["java","-jar","/app/YM-0.0.1-SNAPSHOT.jar"]