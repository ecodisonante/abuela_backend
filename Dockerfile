FROM eclipse-temurin:21-jdk AS buildstage 
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY pom.xml .
COPY src /app/src
COPY wallet /app/wallet

RUN mvn clean package

FROM eclipse-temurin:21-jdk 

COPY --from=buildstage /app/target/backend-0.0.3-SNAPSHOT.jar /app/backend.jar

COPY wallet /app/wallet

ENV TNS_ADMIN=./wallet
EXPOSE 8081

ENTRYPOINT [ "java", "-jar","/app/backend.jar" ]



