FROM java:8
VOLUME /tmp
EXPOSE 8080
ADD target/onlineclinic-0.0.1-SNAPSHOT.jar onlineclinic-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","onlineclinic-0.0.1-SNAPSHOT.jar"]