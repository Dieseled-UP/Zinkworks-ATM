FROM openjdk:11-slim
ENV PORT 8080
ENV CLASSPATH /opt/lib
EXPOSE 8080

COPY target/Zinkworks-ATM-0.0.1-SNAPSHOT.jar Zinkworks-ATM-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","Zinkworks-ATM-0.0.1-SNAPSHOT.jar"]
