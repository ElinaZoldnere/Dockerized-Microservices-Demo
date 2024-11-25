# Dockerized Microservices Demo
This repository contains a microservice architecture deployed in Docker containers. It demonstrates distributed systems 
principles and containerization using Spring Boot, RabbitMQ, Docker Compose, and Elastic Stack for monitoring.

# Overview
The core of the system is the insurance calculator application web service, which calculates insurance premiums based on
user requests. The project extends the core application into a microservice architecture by introducing two additional 
services:
- **Blacklist Service**: A synchronously integrated service to verify whether a person is not on a blacklist.
- **Proposal Generator**: An asynchronously integrated service (via RabbitMQ) for generating insurance proposals in 
PDF format.
The system uses RabbitMQ for message brokering and MySQL as the database. For monitoring and observability, the 
Elastic Stack is utilized, including Filebeat, Metricbeat, Logstash, Elasticsearch, and Kibana for data visualization.

All services and supporting components are containerized and orchestrated using Docker Compose. Once the necessary
custom images are created, the entire system can be started with a single command.

## Key Features
- Microservices architecture: 10 Docker containers, each representing a distinct service, for modular development and 
deployment.
- Spring Boot applications: builds, dependencies, and Docker integration managed with Gradle.
- Configuration management: externalized environment variables and configurations, and Spring Profiles for different
environments.
- Message brokering: RabbitMQ for asynchronous communication, featuring Dead-Letter Queues and Exchanges to handle
message failures.
- Database management: automated database initialization using `mysql-init`, and schema migrations with Liquibase.
- Container orchestration: services coordinated using Docker Compose.
- Monitoring and observability: Elastic Stack (Filebeat, Metricbeat, Logstash, Elasticsearch, and Kibana) for
 monitoring and visualization.
- Data persistence: mounted volumes for export data persistence.
- Local access support: provided local access through port mappings.

> More information about the core application, its features, and design you can find here:
[Insurance Application Part 1](https://github.com/ElinaZoldnere/Insurance-Application-Part-1).
