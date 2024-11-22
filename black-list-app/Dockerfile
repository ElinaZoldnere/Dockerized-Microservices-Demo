FROM eclipse-temurin:21

# Install bash, if needed (already installed for Temurin, Debian, Ubuntu, but not for Alpine)
# RUN apk add --no-cache bash

COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x /wait-for-it.sh

COPY black-list-1.0.0.jar black-list.jar
CMD ["./wait-for-it.sh", "mysql-container:3306", "-t", "45", "--", "java", "-jar", "black-list.jar"]
EXPOSE 8080

# Time zone environment variable
ENV TZ=Europe/Helsinki
