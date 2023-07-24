FROM openjdk:20

ENV ENVIRONMENT=prod

EXPOSE 8080

LABEL maintainer="Zakrek.lt@gmail.com"

ADD backend/target/sprottenguide.jar app.jar

CMD [ "sh", "-c", "java -jar /app.jar" ]