FROM openjdk:8u212-jdk-alpine3.9
EXPOSE 8080
RUN wget https://github.com/grails/grails-core/releases/download/v4.0.3/grails-4.0.3.zip && unzip grails-4.0.3.zip -d /usr/lib && rm grails-4.0.3.zip
ENV GRAILS_HOME=/usr/lib/grails-4.0.3
ENV PATH=$PATH:$GRAILS_HOME/bin
WORKDIR /usr/b2b-rest-app
ADD . .
RUN grails package
