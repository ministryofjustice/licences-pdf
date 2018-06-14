#!/usr/bin/env bash

./gradlew build
docker build -t licencespdf .

# To run within Docker:
# docker run -d -p 8080:8080 --name licencespdf -e DEBUG_LOG=true licencespdf