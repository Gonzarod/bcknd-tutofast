FROM openjdk:8
RUN mvn clean
RUN mvn package -DskipTests=false
ADD target/bcknd-tutofast.jar bcknd-tutofast.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "bcknd-tutofast.jar"]
