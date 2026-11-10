# syntax=docker/dockerfile:1
FROM maven:3.9-eclipse-temurin-21-alpine AS dependencies
ENV MAVEN_OPTS="-Xmx256m"
WORKDIR /build
COPY pom.xml ./
RUN --mount=type=cache,id=finworkspace-maven,target=/root/.m2,sharing=locked \
    mvn -B -ntp dependency:go-offline

FROM dependencies AS build
COPY src ./src
# Context tests requiring external services are run separately, not while packaging.
RUN --mount=type=cache,id=finworkspace-maven,target=/root/.m2,sharing=locked \
    mvn -B -ntp -DskipTests package && cp target/*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract --destination /layers

FROM maven:3.9-eclipse-temurin-21-alpine AS java-runtime
# Retain Java SE and service-provider modules needed by Spring, TLS and JDBC.
RUN jlink -J-Xmx256m --add-modules java.se,jdk.crypto.ec,jdk.unsupported,jdk.management,jdk.naming.dns,jdk.charsets,jdk.localedata,jdk.zipfs \
    --strip-debug --no-man-pages --no-header-files --compress=2 --output /opt/java-minimal

FROM alpine:3.23 AS production
RUN apk add --no-cache ca-certificates libstdc++ zlib tzdata \
    && addgroup -S -g 10001 app && adduser -S -D -H -u 10001 -G app app
ENV JAVA_HOME=/opt/java PATH="/opt/java/bin:${PATH}"
WORKDIR /app
COPY --from=java-runtime /opt/java-minimal /opt/java
COPY --from=build /layers/dependencies/ ./
COPY --from=build /layers/spring-boot-loader/ ./
COPY --from=build /layers/snapshot-dependencies/ ./
COPY --from=build /layers/application/ ./
USER 10001:10001
EXPOSE 8081
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
