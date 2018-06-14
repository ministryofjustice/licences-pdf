FROM openjdk:8-jre-alpine

COPY ./build/artifacts/licencespdf.jar /root/licencespdf.jar

EXPOSE 8080

ENTRYPOINT ["/usr/bin/java", "-jar", "/root/licencespdf.jar"]
