FROM openjdk:11
COPY target/product-ms-0.0.1-SNAPSHOT.jar product-ms-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/product-ms-0.0.1-SNAPSHOT.jar"]