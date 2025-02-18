## Build stage
#FROM maven:3.8.7-openjdk-17 AS build
#WORKDIR /build
#COPY pom.xml .
#RUN mvn dependency:go-offline
#COPY src ./src
#RUN mvn clean package -DskipTests
#
## Runtime stage
#FROM amazoncorretto:17
#
#WORKDIR /app
#COPY --from=build /build/target/goodTripBackend-*.jar /app/
#EXPOSE 8080
#CMD java -jar
#ENTRYPOINT ["java", "-jar", "goodTripBackend.jar"]
FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /build
COPY pom.xml .


#RUN gradle installDist

#CMD ./build/install/goodTripBackend/bin/goodTripBackend