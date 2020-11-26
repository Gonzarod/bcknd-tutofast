FROM openjdk:8
ADD target/bcknd-tutofast.jar bcknd-tutofast.jar
RUN mvn clean
RUN mvn package -DskipTests=false
EXPOSE 8080
ENTRYPOINT ["java","-jar", "bcknd-tutofast.jar"]
