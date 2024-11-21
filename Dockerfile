FROM openjdk:11-jdk-slim

WORKDIR /app

COPY . /app

RUN apt-get update && apt-get install -y \
    openjdk-11-jre-headless \
    maven \
    && rm -rf /var/lib/apt/lists/*

RUN bash -c 'cd /app && mvn clean package -DskipTests'

EXPOSE 8081

CMD ["java", "-cp", "/app/target/classes:/app/target/dependency/*", "org.ordermanagement.PocOrderManagementApplication"]
