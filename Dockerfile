FROM tomcat:jdk11-openjdk

LABEL maintainer=”justinshagerty@gmail.com”

# RUN sed -i 's/port="8080"/port="80"/' ${CATALINA_HOME}/conf/server.xml

ADD ./target/Kafka_As_A_Service.war /usr/local/tomcat/webapps/

EXPOSE 80