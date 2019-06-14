FROM openjdk:11-slim
MAINTAINER HMPPS Digital Studio <info@digital.justice.gov.uk>

RUN apt-get update && \
    apt-get install -y curl && \
    rm -rf /var/lib/apt/lists/*

ENV TZ=Europe/London
RUN ln -snf "/usr/share/zoneinfo/$TZ" /etc/localtime && echo "$TZ" > /etc/timezone

RUN addgroup --gid 2000 --system appgroup && \
    adduser --uid 2000 --system appuser --gid 2000

WORKDIR /app

COPY ./build/libs/licencespdf-*.jar /app/licencespdf.jar

RUN chown -R appuser:appgroup /app

USER 2000

ENTRYPOINT ["/usr/bin/java", "-jar", "/app/licencespdf.jar"]
