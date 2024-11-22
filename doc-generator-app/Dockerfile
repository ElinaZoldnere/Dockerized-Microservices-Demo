FROM eclipse-temurin:21

# Install bash, if needed (already installed for Temurin, Debian, Ubuntu, but not for Alpine)
# RUN apk add --no-cache bash

COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x /wait-for-it.sh

COPY doc-generator-1.0.0.jar doc-generator.jar
CMD ["./wait-for-it.sh", "rabbitmq-container:5672", "-t", "30", "--", "java", "-jar", "doc-generator.jar"]
EXPOSE 8080

# Time zone environment variable
ENV TZ=Europe/Helsinki
