# Build Stage
FROM maven:3.9.11-eclipse-temurin AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
#RUN mvn clean package -DskipTests
RUN mvn clean package -Dmaven.test.skip=true

# Runtime Stage
FROM amazoncorretto:25
ARG PROFILE=prod
ARG APP_VERSION=1.0.0

WORKDIR /app
COPY --from=build /build/target/booking-management-system-*.jar /app/

EXPOSE 8080

ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}

CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} booking-management-system-${JAR_VERSION}.jar
