FROM eclipse-temurin:21-jre-jammy AS jar

RUN apt-get update && apt-get install -y curl && apt-get clean && rm -rf /var/lib/apt/lists/* /var/cache/apt/archives/*

RUN addgroup --system spring && adduser --system spring --ingroup spring

USER spring:spring

COPY --chown=spring ./target/*.jar ./tickets-api.jar

ENTRYPOINT ["java","-jar","/tickets-api.jar"]