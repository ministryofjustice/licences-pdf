FROM openjdk:11-slim
MAINTAINER HMPPS Digital Studio <info@digital.justice.gov.uk>

RUN apt-get update && apt-get install -y curl
WORKDIR /app

COPY ./build/libs/licencespdf-*.jar /app/licencespdf.jar

EXPOSE 8080

ENTRYPOINT ["/usr/bin/java", "-jar", "/app/licencespdf.jar"]
