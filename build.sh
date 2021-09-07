#!/bin/bash

ls -la 
../../java/apache-maven-3.8.1/bin/mvn package -f ./pom.xml
ls -la
# might want to change these to your docker instances/tags
../../Program\ Files/Docker/Docker/resources/bin/docker.exe build -t justinshagerty/kafka_as_a_service .
../../Program\ Files/Docker/Docker/resources/bin/docker.exe run -it -p 80:80 justinshagerty/kafka_as_a_service


