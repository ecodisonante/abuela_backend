FROM eclipse-temurin:21-jdk AS buildstage 
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY pom.xml .
COPY src /app/src
COPY wallet /app/wallet

ENV TNS_ADMIN=./wallet
ENV DB_URL=jdbc:oracle:thin:@segrecetas_medium?TNS_ADMIN=/app/wallet_docker
ENV DB_USERNAME=USR_ABUELA
ENV DB_PASSWORD=Abu3l4D1g1t4l
ENV USR_TEST_PASSWORD=Secret123

RUN mvn clean package

FROM eclipse-temurin:21-jdk 

COPY --from=buildstage /app/target/backend-0.0.2-SNAPSHOT.jar /app/backend.jar

COPY wallet /app/wallet

ENV TNS_ADMIN=./wallet
EXPOSE 8081

ENTRYPOINT [ "java", "-jar","/app/backend.jar" ]



