FROM maven:3.6.2-jdk-12 AS horizon_appserver
WORKDIR /usr/src/horizonbackend
VOLUME $PWD:/usr/src/mymaven
VOLUME $HOME/.m2:/root/.m2
VOLUME $PWD/target:/usr/src/mymaven/target
COPY pom.xml .
RUN mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
COPY . .
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package -DskipTests

FROM openjdk:8-jdk-alpine as horizon_javaserver
WORKDIR /app
COPY --from=horizon_appserver /usr/src/horizonbackend/target/horizon-backend-1.0-SNAPSHOT.jar .
#COPY /target/docker-spring-1.0-SNAPSHOT.jar .
EXPOSE 5005
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005","-jar", "/app/horizon-backend-1.0-SNAPSHOT.jar"]
CMD ["--spring.profiles.active=postgres"]
