FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ADD ./target/api-empresa-3.2.5.jar api-empresa.jar
ENTRYPOINT ["java","-jar","/api-empresa.jar"]