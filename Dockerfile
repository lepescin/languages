FROM openjdk:17
ADD /target/languages-0.0.1-SNAPSHOT.jar languages.jar
ENTRYPOINT ["java", "-jar", "languages.jar"]
EXPOSE 8080