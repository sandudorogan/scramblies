FROM openjdk:8-alpine

COPY target/uberjar/scramblies.main.jar /scramblies.main/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/scramblies.main/app.jar"]
